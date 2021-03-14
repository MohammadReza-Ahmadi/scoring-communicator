package com.vosouq.scoringcommunicator.controllers;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.scoringcommunicator.services.CredentialProfileService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import org.springframework.web.bind.annotation.*;


//@RestController
@VosouqRestController
@RequestMapping("/credential-profiles")
public class CredentialProfileController {

    private CredentialProfileService credentialProfileService;

    public CredentialProfileController(CredentialProfileService credentialProfileService) {
        this.credentialProfileService = credentialProfileService;
    }

    @Created
    @PostMapping(value = "")
    public void createProfile() {
        credentialProfileService.create();
    }

    @PutMapping(value = "/{publicity}")
    public void changeProfilePublicity(@PathVariable boolean publicity) {
        credentialProfileService.updateProfilePublicView(publicity);
    }
}
