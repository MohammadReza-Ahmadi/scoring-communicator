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
public class ChequesStatusRaw {
    @JsonProperty("unfixed_returned_cheques_count_of_last_3_months")
    private Integer unfixedReturnedChequesCountOfLast3Months;

    @JsonProperty("unfixed_returned_cheques_count_between_last_3_to_12_months")
    private Integer unfixedReturnedChequesCountBetweenLast3To12Months;

    @JsonProperty("unfixed_returned_cheques_count_of_more_12_months")
    private Integer unfixedReturnedChequesCountOfMore12Months;

    @JsonProperty("unfixed_returned_cheques_count_of_last_5_years")
    private Integer unfixedReturnedChequesCountOfLast5Years;

    @JsonProperty("unfixed_returned_cheques_total_balance")
    private Long unfixedReturnedChequesTotalBalance;
}
