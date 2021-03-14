package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.controllers.dtos.res.CredentialCheckRequestRes;
import com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil;
import com.vosouq.scoringcommunicator.models.CredentialCheckRequest;
import com.vosouq.scoringcommunicator.models.RequestStatus;
import com.vosouq.scoringcommunicator.models.RequestType;
import com.vosouq.scoringcommunicator.repositories.UserProfileRepositoryMOC;
import com.vosouq.scoringcommunicator.services.CredentialCheckRequestService;
import com.vosouq.scoringcommunicator.services.UserBusinessService;
import com.vosouq.scoringcommunicator.services.ValidationService;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.vosouq.scoringcommunicator.infrastructures.Constants.*;

@Service
public class CredentialCheckRequestServiceImpl implements CredentialCheckRequestService {
    private JongoService jongoService;
    private MongoCollection collection;
    private UserBusinessService userBusinessService;
    private ValidationService validationService;
    //todo: should be replace by actual service
    private UserProfileRepositoryMOC userProfileRepositoryMOC;

    @Value("${creditStatus.credentialRequest.expiryDayCount}")
    private int expiryDayCount;


    public CredentialCheckRequestServiceImpl(JongoService jongoService, UserBusinessService userBusinessService, ValidationService validationService, UserProfileRepositoryMOC userProfileRepositoryMOC) {
        this.jongoService = jongoService;
        this.userBusinessService = userBusinessService;
        this.validationService = validationService;
        this.userProfileRepositoryMOC = userProfileRepositoryMOC;
        this.collection = this.jongoService.getMongoCollection(CredentialCheckRequest.collectionName);
    }

    @Override
    public void create(CredentialCheckRequest chr) {
        collection.save(chr);
    }

    @Override
    public void create(Long acceptorId) {
        validationService.validateCreateCredentialCheckRequest(userBusinessService.getOnlineUserId());
        collection.save(new CredentialCheckRequest(acceptorId, userBusinessService.getOnlineUserId(), new Date(), CreditScoringUtil.calcLastTimeOfFutureDateByDelta(new Date(), expiryDayCount), ZERO_INT, ONE_INT));
    }

    @Override
    public void updateForAccept(String id) {
        updateStatus(id, ONE_INT);
    }

    @Override
    public void updateForReject(String id) {
        updateStatus(id, MINUS_ONE_INT);
    }

    @Override
    public void updateForInvisibility(String id) {
        collection.update("{ _id: # }", new ObjectId(id))
                .with("{$set: {" + CredentialCheckRequest.Fields.visibility.name() + ": #}}", ZERO_INT);
    }

    @Override
    public List<CredentialCheckRequestRes> findAllRequests(RequestType type) {
        List<CredentialCheckRequestRes> result = new ArrayList<>();
        MultiValuedMap<Long, CredentialCheckRequestRes> map = new HashSetValuedHashMap<>();

        String fieldName = type.isSent() ? CredentialCheckRequest.Fields.applicantId.name() : CredentialCheckRequest.Fields.userId.name();
        collection.find("{" + fieldName + ": #, " + CredentialCheckRequest.Fields.visibility.name() + ": #}", userBusinessService.getOnlineUserId(), ONE_INT)
//                .sort("{" + CredentialCheckRequest.Fields.requestDate.name() + ": " + MINUS_ONE_INT + "}")
                .as(CredentialCheckRequest.class)
                .forEachRemaining(ccr ->
                        {
                            CredentialCheckRequestRes res = new CredentialCheckRequestRes(ccr.getId().toString(), ccr.getRequestDate(), RequestStatus.resolve(ccr.getStatus()));
                            if (type.isSent())
                                map.put(ccr.getUserId(), res);
                            else
                                map.put(ccr.getApplicantId(), res);
                        }
                );

        userProfileRepositoryMOC.getUserProfiles((map.keySet().toArray(new Long[ZERO_INT])))
                .forEach(p -> {
                            map.get(p.getId()).forEach(res ->
                                    {
                                        res.setName(p.getName());
                                        res.setImageUrl(p.getImageUrl());
                                        result.add(res);
                                    }
                            );
                        }
                );


        result.sort((r1, r2) -> r2.getRequestDate().compareTo(r1.getRequestDate()));
        return result;
    }

    @Override
    public List<CredentialCheckRequest> findAllReceivedRequests() {
        List<CredentialCheckRequest> result = new ArrayList<>();
        collection.find("{" + CredentialCheckRequest.Fields.applicantId.name() + ": #, " + CredentialCheckRequest.Fields.visibility.name() + ": #}", userBusinessService.getOnlineUserId(), ONE_INT)
                .sort("{" + CredentialCheckRequest.Fields.requestDate.name() + ": " + MINUS_ONE_INT + "}")
                .as(CredentialCheckRequest.class)
                .iterator()
                .forEachRemaining(result::add);
        return result;
    }

    @Override
    public boolean hasAcceptedNotExpiredRequestForOnlineUser(Long userId) {
        CredentialCheckRequest request = collection.findOne("{" + CredentialCheckRequest.Fields.userId.name() + ": #,"
                + CredentialCheckRequest.Fields.applicantId.name() + ": #," + CredentialCheckRequest.Fields.expiryDate.name()
                + ":{$lte: #}," + CredentialCheckRequest.Fields.status.name() + ": #}", userId, userBusinessService.getOnlineUserId(), new Date(), ONE_INT)
                .as(CredentialCheckRequest.class);
        return CreditScoringUtil.isNotNull(request);
    }

    private void updateStatus(String id, int status) {
        collection.update("{ _id: #, " + CredentialCheckRequest.Fields.userId.name() + ": #}", new ObjectId(id), userBusinessService.getOnlineUserId())
                .with("{$set: {" + CredentialCheckRequest.Fields.status.name() + ": #}}", status);
    }
}
