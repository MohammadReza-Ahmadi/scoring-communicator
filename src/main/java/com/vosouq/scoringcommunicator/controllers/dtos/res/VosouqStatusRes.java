package com.vosouq.scoringcommunicator.controllers.dtos.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VosouqStatusRes {
    private Integer membershipDurationDay;
    private Integer membershipDurationMonth;
    private Integer doneTradesCount;
    private Integer undoneTradesCount;
    private Integer negativeStatusCount;
    private Integer delayDaysCountAvg;
    private Integer recommendToOthersCount;
}
