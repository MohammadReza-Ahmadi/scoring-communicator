package com.vosouq.scoringcommunicator.controllers.dtos.raws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ScoreBoundariesRaw {
    @JsonProperty("min_score")
    private Integer minScore;

    @JsonProperty("max_score")
    private Integer maxScore;

    @JsonProperty("identities_max_score")
    private Integer identitiesMaxScore;

    @JsonProperty("histories_max_score")
    private Integer historiesMaxScore;

    @JsonProperty("volumes_max_score")
    private Integer volumesMaxScore;

    @JsonProperty("timeliness_max_score")
    private Integer timelinessMaxScore;
}
