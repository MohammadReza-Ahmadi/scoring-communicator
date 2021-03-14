package com.vosouq.scoringcommunicator.services;

import com.vosouq.scoringcommunicator.models.CredentialProfile;

public interface CredentialProfileService {

    void create();

    void updateProfilePublicView(boolean publicity);

    CredentialProfile findByUserId(Long userId);

    CredentialProfile findByUserId(Long userId, boolean nullSafe);
}
