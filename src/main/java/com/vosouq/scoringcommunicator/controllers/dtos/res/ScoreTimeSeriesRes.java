package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreTimeSeriesRes {
    @JsonProperty("score_date")
    private Date score_date;

    @JsonProperty("score")
    private Integer score;
}
