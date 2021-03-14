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
public class ScoreDetailsRaw {
    @JsonProperty("score")
    private int score;

    @JsonProperty("identities_score")
    private int identitiesScore;

    @JsonProperty("histories_score")
    private int historiesScore;

    @JsonProperty("volumes_score")
    private int volumesScore;

    @JsonProperty("timeliness_score")
    private int timelinessScore;
}
