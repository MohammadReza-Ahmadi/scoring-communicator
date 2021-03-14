package com.vosouq.scoringcommunicator.controllers.dtos.res;


import com.vosouq.scoringcommunicator.models.RequestStatus;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
public class CredentialCheckRequestRes {
    @NonNull
    private String id;

    private String name;

    private String imageUrl;

    @NonNull
    private Date requestDate;

    @NonNull
    private RequestStatus status;
}
