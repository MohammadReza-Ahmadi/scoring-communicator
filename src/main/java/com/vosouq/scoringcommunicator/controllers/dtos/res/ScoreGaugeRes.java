package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreGaugeRes {
    private Integer start;
    private Integer end;
    private String title;
    private String color;
}
