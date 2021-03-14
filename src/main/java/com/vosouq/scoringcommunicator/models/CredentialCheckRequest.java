package com.vosouq.scoringcommunicator.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.bson.types.ObjectId;

import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@FieldNameConstants(asEnum = true)
@Setter
@Getter
@ToString
public class CredentialCheckRequest {
    public static String collectionName = "credentialCheckRequests";

    @JsonProperty(value = "_id")
    @FieldNameConstants.Exclude
    private ObjectId id;

    @NonNull
    private Long userId;

    @NonNull
    private Long applicantId;

    @NonNull
    private Date requestDate;

    @NonNull
    private Date expiryDate;

    @NonNull
    private int status;

    @NonNull
    private int visibility;
}
