package com.vosouq.scoringcommunicator.controllers.dtos.res;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class TripleRes {
    @NonNull
    private String title;
    @NonNull
    private String value;
    private String unit;
}
