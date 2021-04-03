package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vosouq.scoringcommunicator.controllers.dtos.raws.ScoreGaugeRaw;
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
public class ScoreStatusRes {

    private UserProfileRes otherUserProfile;
    private Integer currentScore;
    private Integer maxScore;
    private Integer scoreChange;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date lastUpdateDate;
    private List<ScoreGaugeRes> ranges;


//    @SerializedName("currentScore")
//    val mCurrentScore: Int?,
//    @SerializedName("lastUpdateDate")
//    val mLastUpdateDate: Long?,
//    @SerializedName("maxScore")
//    val mMaxScore: Int?,
//    @SerializedName("ranges")
//    val mScoreRange: List<ScoreRange>?
}
