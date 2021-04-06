package com.vosouq.scoringcommunicator.controllers.demo;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.LoansStatusRaw;
import com.vosouq.scoringcommunicator.controllers.dtos.raws.ScoreGaugeRaw;
import com.vosouq.scoringcommunicator.controllers.dtos.raws.ScoreStatusRaw;
import com.vosouq.scoringcommunicator.controllers.dtos.raws.VosouqStatusRaw;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.services.impl.CredentialCheckRequestServiceImpl;
import com.vosouq.scoringcommunicator.repositories.ScoringEngineRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil.getDate;

//@RestController
//@VosouqRestController
@RequestMapping(value = "/credit-status-demo")
public class CreditStatusControllerDEMO {

    private CredentialCheckRequestServiceImpl credentialCheckRequestServiceImpl;
    private ScoringEngineRepository scoringEngineRepository;

    public CreditStatusControllerDEMO(CredentialCheckRequestServiceImpl credentialCheckRequestServiceImpl, ScoringEngineRepository scoringEngineRepository) {
        this.credentialCheckRequestServiceImpl = credentialCheckRequestServiceImpl;
        this.scoringEngineRepository = scoringEngineRepository;
    }

    @GetMapping(value = {"/test-gauges", "/test-gauges/{userId}"})
    public List<ScoreGaugeRaw> getScoreGauges(@PathVariable(required = false) Long userId) {
        return scoringEngineRepository.getScoreGauges();
    }

    @GetMapping(value = {"", "/{userId}"})
    public ScoreStatusRaw getScoreStatuses(@PathVariable(required = false) Long userId) {
        ScoreStatusRaw ssr = new ScoreStatusRaw();
        ssr.setScore(660);
        ssr.setLastScoreChange(15);
//        ssr.setLastUpdateDate(new Date().getTime());

        List<ScoreGaugeRaw> gauges = new ArrayList<>();
        ScoreGaugeRaw scoreGaugeRaw;

        scoreGaugeRaw = new ScoreGaugeRaw(0, 500, "خیلی ضعیف", "fe3030", "بسیار بالا");
        gauges.add(scoreGaugeRaw);

        scoreGaugeRaw = new ScoreGaugeRaw(500, 600, "ضعیف", "ff6800", "بالا");
        gauges.add(scoreGaugeRaw);

        scoreGaugeRaw = new ScoreGaugeRaw(600, 700, "متوسط", "ffbb5e", "متوسط");
        gauges.add(scoreGaugeRaw);

        scoreGaugeRaw = new ScoreGaugeRaw(700, 800, "خوب", "a6d94c", "پایین");
        gauges.add(scoreGaugeRaw);

        scoreGaugeRaw = new ScoreGaugeRaw(800, 1000, "عالی", "00d184", "بسیار پایین");
        gauges.add(scoreGaugeRaw);

        ssr.setScoreGaugeRaws(gauges);
        return ssr;
    }

    @GetMapping(value = {"/vosouqs", "/vosouqs/{userId}"})
    public VosouqStatusRaw getVosouqStatuses(@PathVariable(required = false) Long userId) {
        VosouqStatusRaw vsr = new VosouqStatusRaw();
        vsr.setMembershipDurationDay(11);
        vsr.setMembershipDurationMonth(4);
        vsr.setDoneTradesCount(12);
        vsr.setUndoneTradesCount(1);
        vsr.setNegativeStatusCount(0);
        vsr.setDelayDaysCountAvg(1);
        vsr.setRecommendToOthersCount(7);
        return vsr;
    }


    @GetMapping(value = {"/loans", "/loans/{userId}"})
    public LoansStatusRaw getLoanStatuses(@PathVariable(required = false) Long userId) {
        LoansStatusRaw lsr = new LoansStatusRaw();
//        lsr.setCurrentLoansCount(3);
//        lsr.setPastDueLoansAmount(2);
//        lsr.setArrearsLoansAmount(1);
//        lsr.setSuspiciousLoansAmount(0);
        return lsr;
    }

    @GetMapping(value = {"/cheques", "/cheques/{userId}"})
    public List<ChequesStatusRes> getChequeStatuses(@PathVariable(required = false) Long userId) {
        List<ChequesStatusRes> csrList = new ArrayList<>();
        IntStream.range(1, 4).forEach(i ->
                csrList.add(new ChequesStatusRes("", i, (long) (10000 * i)))
        );
        return csrList;
    }

    @GetMapping(value = {"/changes", "/changes/{userId}"})
    public List<ScoreChangeProcessRes> getScoreChangesProcess(@PathVariable(required = false) Long userId) {
        List<ScoreChangeProcessRes> changeRes = new ArrayList<>();
        changeRes.add(new ScoreChangeProcessRes(570, getDate(2020, 3, 20)));
        changeRes.add(new ScoreChangeProcessRes(530, getDate(2020, 4, 20)));
        changeRes.add(new ScoreChangeProcessRes(600, getDate(2020, 5, 20)));
        changeRes.add(new ScoreChangeProcessRes(610, getDate(2020, 6, 22)));
        changeRes.add(new ScoreChangeProcessRes(650, getDate(2020, 7, 22)));
        changeRes.add(new ScoreChangeProcessRes(560, getDate(2020, 8, 22)));
        changeRes.add(new ScoreChangeProcessRes(620, getDate(2020, 9, 22)));
        return changeRes;
    }

    @GetMapping(value = {"/details", "/details/{userId}"})
    public ScoreDetailsRes getScoreDetails(@PathVariable(required = false) Long userId) {
        ScoreDetailsRes sdr = new ScoreDetailsRes();

        //set chartItems
        List<ScoreDetailsRes.ChartItem> chartItems = new ArrayList<>();
        chartItems.add(sdr.new ChartItem(100, 5, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(200, 20, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(300, 15, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(400, 25, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(500, 15, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(600, 20, "ffbb5e"));
        chartItems.add(sdr.new ChartItem(700, 35, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(800, 20, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(900, 35, "A9A9A9"));
        chartItems.add(sdr.new ChartItem(1000, 25, "A9A9A9"));
        sdr.setChartItems(chartItems);

        //set percentile
        sdr.setPercentile(sdr.new Percentile(10, 6));

        //set details
        List<ScoreDetailsRes.Detail> details = new ArrayList<>();
        details.add(sdr.new Detail("اطلاعات هویتی", 70, 100));
        details.add(sdr.new Detail("سوابق تعاملات", 125, 250));
        details.add(sdr.new Detail("حجم تعاملات", 200, 300));
        details.add(sdr.new Detail("انجام به موقع تعاملات", 300, 350));
        sdr.setDetails(details);

        return sdr;
    }


    @GetMapping(value = {"/reasons", "/reasons/{userId}"})
    public List<ScoreChangeRes> getScoreChangeReasons(@PathVariable(required = false) Long userId) {
        List<ScoreChangeRes> reasons = new ArrayList<>();
        reasons.add(new ScoreChangeRes("پایان موفقیت آمیز معامله امن", getDate(2020, 3, 20), 10));
        reasons.add(new ScoreChangeRes("تعداد تعاملات موفق در سه ماه گذشته", getDate(2020, 4, 20), 15));
        reasons.add(new ScoreChangeRes("تاخیر در انجام معامله امن", getDate(2020, 5, 20), -7));
        reasons.add(new ScoreChangeRes("احراز اصالت محل سکونت", getDate(2020, 6, 22), 6));
        reasons.add(new ScoreChangeRes("تکمیل فرآیند احراز هویت", getDate(2020, 7, 22), 5));
        return reasons;
    }


    //    --------- first impl --------------------
//    @PostMapping
//    public Long create(@RequestBody CreditHistoryRequest chr) {
//        creditHistoryService.save(chr);
//        return chr.getUserId();
//    }
//
//    @GetMapping("/{userId}")
//    public CreditHistoryResponse get(@PathVariable long userId) {
//        CreditHistoryRequest chr = creditHistoryService.findByUserId(userId);
//        return new CreditHistoryResponse(chr.getUserId());
//    }

}
