package com.vosouq.scoringcommunicator.controllers.dtos.raws;

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
public class ScoreChangeRaw {
    @JsonProperty("change_reason")
    private String changeReason;

    @JsonProperty("change_date")
    private Date changeDate;

    @JsonProperty("score_change")
    private Integer scoreChange;
}
