package com.vosouq.scoringcommunicator.controllers.dtos.raws;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vosouq.scoringcommunicator.controllers.dtos.res.UserProfileRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ScoreStatusRaw {

    private UserProfileRes otherUserProfile;

    @JsonProperty("score")
    private Integer score;

    @JsonProperty("max_score")
    private Integer maxScore;

    @JsonProperty("last_score_change")
    private Integer lastScoreChange;

    @JsonProperty("last_update_date")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date lastUpdateDate;

    @JsonProperty("score_gauges")
    private List<ScoreGaugeRaw> scoreGaugeRaws;
}
