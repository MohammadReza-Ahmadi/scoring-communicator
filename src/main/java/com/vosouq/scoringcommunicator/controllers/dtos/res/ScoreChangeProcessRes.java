package com.vosouq.scoringcommunicator.controllers.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreChangeProcessRes {
    private Integer score;
    private Date date;
}
