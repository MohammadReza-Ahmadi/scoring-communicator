package com.vosouq.scoringcommunicator.repositories;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.*;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "scoring-engine", url = "http://127.0.0.1:8016")
public interface ScoringEngineRepository {

    @GetMapping(value = "/score-gauges")
    List<ScoreGaugeRaw> getScoreGauges();

    @GetMapping(value = "/score-boundaries")
    ScoreBoundariesRes getScoreBoundaries();

    @GetMapping(value = "/score-status/{userId}")
    ScoreStatusRaw getScoreStatus(@PathVariable(value = "userId") Long userId);

    @GetMapping(value = "/vosouq-status/{userId}")
    VosouqStatusRes getVosouqStatus(@PathVariable(value = "userId") Long userId);

    @GetMapping(value = "/loans-status/{userId}")
    LoansStatusRes getLoansStatus(@PathVariable(value = "userId") Long userId);

    @GetMapping(value = "/cheques-status/{userId}")
    ChequesStatusRaw getChequesStatus(@PathVariable(value = "userId") Long userId);

    @GetMapping(value = "/score-distributions")
    List<ScoreDistributionRaw> getScoreDistributions();

    @GetMapping(value = "/score-time-series/{userId}/month-filter/{monthFilter}")
    List<ScoreTimeSeriesRes> getScoreTimeSeries(@PathVariable(value = "userId") Long userId, @PathVariable(value = "monthFilter") Integer monthFilter);

    @GetMapping(value = "/score-details/{userId}")
    ScoreDetailsRaw getScoreDetails(@PathVariable(value = "userId") Long userId);

    @GetMapping(value = "/score-changes/{userId}")
    List<ScoreChangeRes> getScoreChanges(@PathVariable(value = "userId") Long userId);
}
