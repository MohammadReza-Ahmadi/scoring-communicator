package com.vosouq.scoringcommunicator.controllers;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.scoringcommunicator.controllers.dtos.raws.LoansStatusRes;
import com.vosouq.scoringcommunicator.controllers.dtos.res.*;
import com.vosouq.scoringcommunicator.services.CreditStatusService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@RestController
@VosouqRestController
@RequestMapping(value = "/credit-status")
public class CreditStatusController {

    private UserBusinessService userBusinessService;
    private CreditStatusService creditStatusService;

    public CreditStatusController(UserBusinessService userBusinessService, CreditStatusService creditStatusService) {
        this.userBusinessService = userBusinessService;
        this.creditStatusService = creditStatusService;
    }

    @GetMapping(value = {"/report/{userId}"})
    public List<ScoreReportRes> getScoreReport(@PathVariable Long userId) {
        return creditStatusService.getScoreReport(userId);
    }

    /**
     * @param userId : other userId
     * @return
     */
    @GetMapping(value = {"", "/{userId}"})
    public ScoreStatusRes getScoreStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreStatus(userId);
    }

    @GetMapping(value = {"/vosouq", "/vosouq/{userId}"})
    public VosouqStatusRes getVosouqStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getVosouqStatus(userId);
    }

    @GetMapping(value = {"/loans", "/loans/{userId}"})
    public LoansStatusRes getLoansStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getLoansStatus(userId);
    }

    @GetMapping(value = {"/cheques", "/cheques/{userId}"})
    public List<ChequesStatusRes> getChequesStatus(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getChequesStatus(userId);
    }

    @GetMapping(value = {"/time-series/filter/{numberOfDays}", "/time-series/{userId}/filter/{numberOfDays}"})
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(@PathVariable(required = false) Long userId, @PathVariable Integer numberOfDays) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreTimeSeries(userId, numberOfDays);
    }

    @GetMapping(value = {"/details", "/details/{userId}"})
    public ScoreDetailsRes getScoreDetails(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreDetails(userId);
    }

    @GetMapping(value = {"/changes", "/changes/{userId}"})
    public List<ScoreChangeRes> getScoreChanges(@PathVariable(required = false) Long userId) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreChanges(userId);
    }
}
