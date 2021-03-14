package com.vosouq.scoringcommunicator.controllers.dtos.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class LoansStatusRes {
    @JsonProperty("current_loans_count")
    private Integer currentLoansCount;

    @JsonProperty("past_due_loans_amount")
    private BigDecimal pastDueLoansAmount;

    @JsonProperty("arrears_loans_amount")
    private BigDecimal arrearsLoansAmount;

    @JsonProperty("suspicious_loans_amount")
    private BigDecimal suspiciousLoansAmount;
}
