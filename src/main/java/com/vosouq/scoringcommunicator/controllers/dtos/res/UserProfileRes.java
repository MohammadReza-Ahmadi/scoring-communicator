package com.vosouq.scoringcommunicator.controllers.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfileRes {
    private Long id;
    private String name;
    private String imageUrl;
}
