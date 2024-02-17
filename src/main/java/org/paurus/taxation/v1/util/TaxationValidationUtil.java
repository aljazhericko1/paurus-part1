package org.paurus.taxation.v1.util;

import lombok.experimental.UtilityClass;
import org.paurus.taxation.v1.exception.RequestValidationException;
import org.paurus.taxation.v1.model.TaxationRequest;

@UtilityClass
public class TaxationValidationUtil {

    public static void validateTaxationRequest(TaxationRequest taxationRequest) {
        if (taxationRequest == null || taxationRequest.getTraderId() == null || taxationRequest.getDecimalOdd() == null || taxationRequest.getPlayedAmount() == null) {
            throw new RequestValidationException();
        }

        if (taxationRequest.getPlayedAmount() <= 0) {
            throw new RequestValidationException();
        }

        if (taxationRequest.getDecimalOdd() < 1.) {
            throw new RequestValidationException();
        }
    }
}
