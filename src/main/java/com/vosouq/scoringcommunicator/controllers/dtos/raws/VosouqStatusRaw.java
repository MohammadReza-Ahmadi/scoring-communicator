package com.vosouq.scoringcommunicator.controllers.dtos.raws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class VosouqStatusRaw {
    @JsonProperty("membership_duration_day")
    private Integer membershipDurationDay;

    @JsonProperty("membership_duration_month")
    private Integer membershipDurationMonth;

    @JsonProperty("done_trades_count")
    private Integer doneTradesCount;

    @JsonProperty("undone_trades_count")
    private Integer undoneTradesCount;

    @JsonProperty("negative_status_count")
    private Integer negativeStatusCount;

    @JsonProperty("delay_days_count_avg")
    private Integer delayDaysCountAvg;

    @JsonProperty("recommend_to_others_count")
    private Integer recommendToOthersCount;
}
