package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.*;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.infrastructures.Messages;
import com.vosouq.scoringcommunicator.repositories.ScoringEngineRepository;
import com.vosouq.scoringcommunicator.repositories.UserProfileRepositoryMOC;
import com.vosouq.scoringcommunicator.services.CreditStatusService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import com.vosouq.scoringcommunicator.services.ValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.THREE_INT;

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
        ScoreStatusRaw scoreStatusRaw = scoringEngineRepository.getScoreStatus(userId);
        scoreStatusRaw.setOtherUserProfile(userProfileRepositoryMOC.getUserProfile(userId));
        return new ScoreStatusRes(
                scoreStatusRaw.getOtherUserProfile(),
                scoreStatusRaw.getScore(),
                scoreStatusRaw.getLastScoreChange(),
                scoreStatusRaw.getLastUpdateDate(),
                scoreStatusRaw.getScoreGaugeRaws().stream().map(r -> new ScoreGaugeRes(r.getStart(), r.getEnd(), r.getTitle(), r.getColor(), r.getRiskStatus())).collect(Collectors.toList()));
    }

    @Override
    public VosouqStatusRes getVosouqStatus(Long userId) {
        validateUserAccess(userId);
        return scoringEngineRepository.getVosouqStatus(userId);
    }

    @Override
    public LoansStatusRes getLoansStatus(Long userId) {
        validateUserAccess(userId);
        return scoringEngineRepository.getLoansStatus(userId);
    }

    @Override
    public List<ChequesStatusRes> getChequesStatuses(Long userId) {
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
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(Long userId, Integer monthFilter) {
        validateUserAccess(userId);
        return scoringEngineRepository.getScoreTimeSeries(userId, monthFilter);
    }

    @Override
    public ScoreDetailsRes getScoreDetails(Long userId) {
        validateUserAccess(userId);
        ScoreDetailsRes scoreDetailsRes = new ScoreDetailsRes();
        scoreDetailsRes.setChartItems(new ArrayList<>());
        scoreDetailsRes.setDetails(new ArrayList<>());

        // load data from scoring-engine
        List<ScoreGaugeRaw> scoreGaugeRes = scoringEngineRepository.getScoreGauges();
        ScoreBoundariesRes scoreBoundariesRes = scoringEngineRepository.getScoreBoundaries();
        ScoreDetailsRaw scoreDetailsRaw = scoringEngineRepository.getScoreDetails(userId);

        // set chartItems
        List<ScoreDistributionRaw> scoreDistributionRaws = scoringEngineRepository.getScoreDistributions();
        for (ScoreDistributionRaw sdr : scoreDistributionRaws) {
            String color = resolveChartItemColor(scoreGaugeRes, sdr.getFromScore(), sdr.getToScore(), scoreDetailsRaw.getScore());
            scoreDetailsRes.addNewChartItem(sdr.getFromScore(), sdr.getToScore(), color);
        }

        // set Details
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.identities"), scoreDetailsRaw.getIdentitiesScore(), scoreBoundariesRes.getIdentitiesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.histories"), scoreDetailsRaw.getHistoriesScore(), scoreBoundariesRes.getHistoriesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.volumes"), scoreDetailsRaw.getVolumesScore(), scoreBoundariesRes.getVolumesMaxScore());
        scoreDetailsRes.addNewDetail(messages.get("ScoreDetailsRes.Detail.title.timeliness"), scoreDetailsRaw.getTimelinessScore(), scoreBoundariesRes.getTimelinessMaxScore());

        // set Percentile
        int level = (scoreDetailsRaw.getScore() * percentileTotal) / scoreBoundariesRes.getMaxScore();
        scoreDetailsRes.setPercentileData(percentileTotal, level);
        return scoreDetailsRes;
    }

    @Override
    public List<ScoreChangeRes> getScoreChanges(Long userId) {
        validateUserAccess(userId);
        return scoringEngineRepository.getScoreChanges(userId);
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
