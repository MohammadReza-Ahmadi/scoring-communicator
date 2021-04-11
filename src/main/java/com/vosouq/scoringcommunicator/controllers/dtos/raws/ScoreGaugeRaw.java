package com.vosouq.scoringcommunicator.controllers.dtos.raws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreGaugeRaw {
    @JsonProperty("start")
    private Integer start;

    @JsonProperty("end")
    private Integer end;

    @JsonProperty("title")
    private String title;

    @JsonProperty("color")
    private String color;

    @JsonProperty("risk_status")
    private String riskStatus;
}
