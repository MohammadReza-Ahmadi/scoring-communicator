package com.vosouq.scoringcommunicator.controllers;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.scoringcommunicator.controllers.dtos.res.CredentialCheckRequestRes;
import com.vosouq.scoringcommunicator.models.RequestType;
import com.vosouq.scoringcommunicator.services.CredentialCheckRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@VosouqRestController
@RequestMapping(value = "/credential-check-requests")
public class CredentialCheckRequestController {

    private CredentialCheckRequestService credentialCheckRequestService;

    public CredentialCheckRequestController(CredentialCheckRequestService credentialCheckRequestService) {
        this.credentialCheckRequestService = credentialCheckRequestService;
    }

    @GetMapping(value = {"/sent"})
    public List<CredentialCheckRequestRes> getAllSentRequests() {
        return credentialCheckRequestService.findAllRequests(RequestType.SENT);
    }

    @GetMapping(value = {"/received"})
    public List<CredentialCheckRequestRes> getAllReceivedRequests() {
        return credentialCheckRequestService.findAllRequests(RequestType.RECEIVED);
    }

    @Created
    @PostMapping(value = "/{acceptorId}")
    public void createRequest(@PathVariable Long acceptorId) {
        credentialCheckRequestService.create(acceptorId);
    }

    @PutMapping(value = "/accept/{id}")
    public void acceptRequest(@PathVariable String id) {
        credentialCheckRequestService.updateForAccept(id);
    }

    @PutMapping(value = "/reject/{id}")
    public void rejectRequest(@PathVariable String id) {
        credentialCheckRequestService.updateForReject(id);
    }

    @PutMapping(value = "/make-invisible/{id}")
    public void makeInvisibleRequest(@PathVariable String id) {
        credentialCheckRequestService.updateForInvisibility(id);
    }
}
