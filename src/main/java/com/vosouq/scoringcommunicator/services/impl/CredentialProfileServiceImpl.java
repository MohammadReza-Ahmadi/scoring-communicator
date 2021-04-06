package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.infrastructures.Constants;
import com.vosouq.scoringcommunicator.infrastructures.exceptions.CredentialProfileAlreadyExistException;
import com.vosouq.scoringcommunicator.infrastructures.exceptions.CredentialProfileNotFoundException;
import com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil;
import com.vosouq.scoringcommunicator.models.CredentialProfile;
import com.vosouq.scoringcommunicator.services.CredentialProfileService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

@Service
public class CredentialProfileServiceImpl implements CredentialProfileService {

    private JongoService jongoService;
    private MongoCollection collection;
    private UserBusinessService userBusinessService;

    public CredentialProfileServiceImpl(JongoService jongoService, UserBusinessService userBusinessService) {
        this.jongoService = jongoService;
        this.userBusinessService = userBusinessService;
        this.collection = this.jongoService.getMongoCollection(CredentialProfile.collectionName);
    }

    @Override
    public void create() {
        if(CreditScoringUtil.isNotNull(findByUserId(userBusinessService.getOnlineUserId())))
            throw new CredentialProfileAlreadyExistException();
        collection.save(new CredentialProfile(userBusinessService.getOnlineUserId(), Constants.ZERO_INT));
    }

    @Override
    public void updateProfilePublicView(boolean publicity) {
        collection.update("{" + CredentialProfile.Fields.userId.name() + " : # }", userBusinessService.getOnlineUserId())
                .with("{$set: {" + CredentialProfile.Fields.publicView.name() + ": #}}", CreditScoringUtil.getIntValue(publicity));
    }

    @Override
    public CredentialProfile findByUserId(Long userId) {
        return findByUserId(userId, false);
    }

    @Override
    public CredentialProfile findByUserId(Long userId, boolean nullSafe) {
        CredentialProfile profile = collection.findOne("{" + CredentialProfile.Fields.userId.name() + ": #}", userId).as(CredentialProfile.class);
        if (nullSafe && CreditScoringUtil.isNull(profile))
            throw new CredentialProfileNotFoundException();
        return profile;
    }
}
