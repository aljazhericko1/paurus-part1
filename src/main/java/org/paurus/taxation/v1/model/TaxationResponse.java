package org.paurus.taxation.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TaxationResponse {
    @JsonProperty("possibleReturnAmount")
    Double possibleReturnAmount;
    @JsonProperty("possibleReturnAmountBefTax")
    Double possibleReturnAmountBeforeTax;
    @JsonProperty("possibleReturnAmountAfterTax")
    Double possibleReturnAmountAfterTax;
    @JsonProperty("taxRate")
    Double taxRate;
    @JsonProperty("taxAmount")
    Double taxAmount;
}
