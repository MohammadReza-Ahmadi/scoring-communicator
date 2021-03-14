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
public class ScoreDistributionRaw {
    @JsonProperty("from_score")
    private Integer fromScore;

    @JsonProperty("to_score")
    private Integer toScore;

    @JsonProperty("count")
    private Integer count; // count of users
}
