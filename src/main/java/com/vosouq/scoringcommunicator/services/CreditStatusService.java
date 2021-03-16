package com.vosouq.scoringcommunicator.services;

import com.vosouq.scoringcommunicator.controllers.dtos.raws.LoansStatusRes;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CreditStatusService {

    ScoreStatusRes getScoreStatus(@PathVariable Long userId);

    VosouqStatusRes getVosouqStatus(Long userId);

    LoansStatusRes getLoansStatus(Long userId);

    List<ChequesStatusRes> getChequesStatuses(Long userId);

    List<ScoreTimeSeriesRes> getScoreTimeSeries(Long userId, Integer numberOfDays);

    ScoreDetailsRes getScoreDetails(Long userId);

    List<ScoreChangeRes> getScoreChanges(Long userId);
}
