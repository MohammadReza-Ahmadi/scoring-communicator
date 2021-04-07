package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.*;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.infrastructures.Messages;
import com.vosouq.scoringcommunicator.models.UnitType;
import com.vosouq.scoringcommunicator.repositories.ScoringEngineRepository;
import com.vosouq.scoringcommunicator.repositories.UserProfileRepositoryMOC;
import com.vosouq.scoringcommunicator.services.CreditStatusService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import com.vosouq.scoringcommunicator.services.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.*;

@Service
public class CreditStatusServiceImpl implements CreditStatusService {

    private ScoringEngineRepository scoringEngineRepository;
    private ValidationService validationService;
    private UserBusinessService userBusinessService;
    private Messages messages;
    //todo: should be replace by actual service
    private UserProfileRepositoryMOC userProfileRepositoryMOC;

    @Value("${creditStatus.percentile.total}")
    private int percentileTotal;

    public CreditStatusServiceImpl(ScoringEngineRepository scoringEngineRepository, ValidationService validationService, UserBusinessService userBusinessService, Messages messages, UserProfileRepositoryMOC userProfileRepositoryMOC) {
        this.scoringEngineRepository = scoringEngineRepository;
        this.validationService = validationService;
        this.userBusinessService = userBusinessService;
        this.messages = messages;
        this.userProfileRepositoryMOC = userProfileRepositoryMOC;
    }

    @Override
    public ScoreStatusRes getScoreStatus(Long userId) {
        validateUserAccess(userId);
        ScoreStatusRaw raw = scoringEngineRepository.getScoreStatus(userId);
        raw.setOtherUserProfile(userProfileRepositoryMOC.getUserProfile(userId));
        return new ScoreStatusRes(
                raw.getOtherUserProfile(),
                raw.getScore(),
                raw.getMaxScore(),
                raw.getLastScoreChange(),
                raw.getLastUpdateDate(),
                raw.getScoreGaugeRaws().stream().map(r -> new ScoreGaugeRes((r.getStart() > ZERO_INT ? r.getStart() - ONE_INT : r.getStart()), r.getEnd(), r.getTitle(), r.getColor())).collect(Collectors.toList()));
    }

    @Override
    public List<TripleRes> getVosouqStatus(Long userId) {
        validateUserAccess(userId);
        VosouqStatusRaw vsqRaw = scoringEngineRepository.getVosouqStatus(userId);
        boolean vsqNegStatus = vsqRaw.getNegativeStatusCount() > ZERO_INT;
        return List.of(
                new TripleRes(messages.get("TripleRes.membershipDuration.title"), vsqRaw.getMembershipDurationMonth().toString(), messages.getUnitTitle(UnitType.MONTH)),
                new TripleRes(messages.get("TripleRes.doneTrades.title"), vsqRaw.getDoneTradesCount().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.undoneTrades.title"), vsqRaw.getUndoneTradesCount().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.negativeStatus.title"), messages.getHavingTitle(vsqNegStatus)),
                new TripleRes(messages.get("TripleRes.delayAvg.title"), vsqRaw.getDelayDaysCountAvg().toString(), messages.getUnitTitle(UnitType.NUMBER)),
                new TripleRes(messages.get("TripleRes.recommendToOthers.title"), vsqRaw.getRecommendToOthersCount().toString(), messages.getUnitTitle(UnitType.NUMBER))
        );
    }

    @Override
    public List<TripleRes> getScoreReport(Long userId) {
        ChequesStatusRaw chqRaw = scoringEngineRepository.getChequesStatus(userId);
        LoansStatusRaw lnRaw = scoringEngineRepository.getLoansStatus(userId);
        boolean chqNegStatus = chqRaw.getUnfixedReturnedChequesTotalBalance() > ZERO_INT;
        boolean lnNegStatus = lnRaw.getPastDueLoansAmount().add(lnRaw.getArrearsLoansAmount()).add(lnRaw.getSuspiciousLoansAmount()).compareTo(BigDecimal.ZERO) > ZERO_INT;
        // get user's vosouqStatus items
        List<TripleRes> resList = new ArrayList<>(getVosouqStatus(userId));
        // add user's cheques and loans items
        resList.add(new TripleRes(messages.get("TripleRes.unfixedReturnedCheque.title"), messages.getHavingTitle(chqNegStatus)));
        resList.add(new TripleRes(messages.get("TripleRes.loansWithNegativeStatus.title"), messages.getHavingTitle(lnNegStatus)));
        return resList;
    }

    @Override
    public List<TripleRes> getLoansStatus(Long userId) {
        validateUserAccess(userId);
        LoansStatusRaw lnRaw = scoringEngineRepository.getLoansStatus(userId);
        return List.of(
                new TripleRes(messages.get("TripleRes.currentLoansCount.title"), lnRaw.getCurrentLoansCount().toString(), messages.getUnitTitle(UnitType.ITEM)),
                new TripleRes(messages.get("TripleRes.pastDueLoansAmount.title"), lnRaw.getPastDueLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL)),
                new TripleRes(messages.get("TripleRes.arrearsLoansAmount.title"), lnRaw.getArrearsLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL)),
                new TripleRes(messages.get("TripleRes.suspiciousLoansAmount.title"), lnRaw.getSuspiciousLoansAmount().toString(), messages.getUnitTitle(UnitType.RIAL))
        );
    }

    @Override
    public List<ChequesStatusRes> getChequesStatus(Long userId) {
        validateUserAccess(userId);
        ChequesStatusRaw raw = scoringEngineRepository.getChequesStatus(userId);
        List<ChequesStatusRes> resList = new ArrayList<>(THREE_INT);
        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.inLast3Months"),
                raw.getUnfixedReturnedChequesCountOfLast3Months(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.inLastYear"),
                raw.getUnfixedReturnedChequesCountOfLast3Months() + raw.getUnfixedReturnedChequesCountBetweenLast3To12Months(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        resList.add(new ChequesStatusRes(
                messages.get("ChequesStatusRes.title.moreThanOneYear"),
                raw.getUnfixedReturnedChequesCountOfMore12Months() + raw.getUnfixedReturnedChequesCountOfLast5Years(),
                raw.getUnfixedReturnedChequesTotalBalance()));

        return resList;
    }

    @Override
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(Long userId, Integer numberOfDays) {
        validateUserAccess(userId);
        List<ScoreTimeSeriesRaw> raws = scoringEngineRepository.getScoreTimeSeries(userId, numberOfDays);
        return raws.stream().map(r -> new ScoreTimeSeriesRes(r.getScore_date(), r.getScore())).collect(Collectors.toList());
    }

    @Override
    public ScoreDetailsRes getScoreDetails(Long userId) {
        validateUserAccess(userId);
        ScoreDetailsRes scoreDetailsRes = new ScoreDetailsRes();
        scoreDetailsRes.setChartItems(new ArrayList<>());
        scoreDetailsRes.setDetails(new ArrayList<>());

        // load data from scoring-engine
        List<ScoreGaugeRaw> scoreGauges = scoringEngineRepository.getScoreGauges();
        ScoreBoundariesRaw scoreBoundariesRaw = scoringEngineRepository.getScoreBoundaries();
        ScoreDetailsRaw scoreDetailsRaw = scoringEngineRepository.getScoreDetails(userId);

        // set chartItems
        List<ScoreDistributionRaw> scoreDistributionRaws = scoringEngineRepository.getScoreDistributions();
        for (ScoreDistributionRaw sdr : scoreDistributionRaws) {
            String color = resolveChartItemColor(scoreGauges, sdr.getFromScore(), sdr.getToScore(), scoreDetailsRaw.getScore());
            scoreDetailsRes.addNewChartItem(sdr.getFromScore(), sdr.getToScore(), color);
        }

        // set Details
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.identities"), scoreDetailsRaw.getIdentitiesScore(), scoreBoundariesRaw.getIdentitiesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.histories"), scoreDetailsRaw.getHistoriesScore(), scoreBoundariesRaw.getHistoriesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.volumes"), scoreDetailsRaw.getVolumesScore(), scoreBoundariesRaw.getVolumesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.timeliness"), scoreDetailsRaw.getTimelinessScore(), scoreBoundariesRaw.getTimelinessMaxScore());

        // set Percentile
        int level = (scoreDetailsRaw.getScore() * percentileTotal) / scoreBoundariesRaw.getMaxScore();
        scoreDetailsRes.setPercentileData(percentileTotal, level);
        return scoreDetailsRes;
    }

    @Override
    public List<ScoreChangeRes> getScoreChanges(Long userId) {
        validateUserAccess(userId);
        List<ScoreChangeRaw> raws = scoringEngineRepository.getScoreChanges(userId);
        return raws.stream().map(r -> new ScoreChangeRes(r.getChangeReason(), r.getChangeDate(), r.getScoreChange())).collect(Collectors.toList());
    }

    private void validateUserAccess(Long userId) {
        if (userBusinessService.isNotOnlineUser(userId))
            validationService.validateUserAccess(userId);
    }

    private String resolveChartItemColor(List<ScoreGaugeRaw> scoreGaugeRes, Integer min, Integer max, Integer score) {
        if (min <= score && max >= score) {
            return scoreGaugeRes.stream().filter(g -> g.getStart() <= score && g.getEnd() >= score).findFirst().get().getColor();
        }
        return messages.get("ScoreDetailsRes.ChartItem.color.gray");
    }
}
