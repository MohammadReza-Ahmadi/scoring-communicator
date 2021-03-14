package com.vosouq.scoringcommunicator.controllers;

import com.vosouq.commons.annotation.VosouqRestController;
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

    /**
     * @param otherUserId : other userId
     * @return
     */
    @GetMapping(value = {"", "/{otherUserId}"})
    public ScoreStatusRes getScoreStatuses(@PathVariable(required = false) Long otherUserId) {
        Long userId = userBusinessService.resolveUserId(otherUserId);
        return creditStatusService.getScoreStatus(userId);
    }

    @GetMapping(value = {"/vosouq", "/vosouq/{userId}"})
    public VosouqStatusRes getVosouqStatuses(@PathVariable(required = false) Long userId) {
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
        return creditStatusService.getChequesStatuses(userId);
    }

    @GetMapping(value = {"/time-series/month-filter/{monthFilter}", "/time-series/{userId}/month-filter/{monthFilter}"})
    public List<ScoreTimeSeriesRes> getScoreTimeSeries(@PathVariable(required = false) Long userId, @PathVariable Integer monthFilter) {
        userId = userBusinessService.resolveUserId(userId);
        return creditStatusService.getScoreTimeSeries(userId, monthFilter);
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
