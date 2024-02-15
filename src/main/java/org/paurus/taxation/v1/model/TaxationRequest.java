package org.paurus.taxation.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class TaxationRequest {
    @JsonProperty(value = "traderId")
    Long traderId;
    @JsonProperty(value = "playedAmount")
    Long playedAmount;
    @JsonProperty(value = "odd")
    Double decimalOdd;
}
