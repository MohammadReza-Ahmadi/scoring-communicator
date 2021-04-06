package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoansStatusRes {
    private Integer currentLoansCount;
    private BigDecimal pastDueLoansAmount;
    private BigDecimal arrearsLoansAmount;
    private BigDecimal suspiciousLoansAmount;
}
