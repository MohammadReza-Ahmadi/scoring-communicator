package com.vosouq.scoringcommunicator.models;

import lombok.*;
import lombok.experimental.FieldNameConstants;

@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(asEnum = true)
@Setter
@Getter
public class CredentialProfile {
    public static String collectionName = "credentialProfiles";

    @NonNull
    private Long userId;

    private int publicView;
}
