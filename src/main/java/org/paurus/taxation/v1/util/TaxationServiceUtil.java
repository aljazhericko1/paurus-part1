package org.paurus.taxation.v1.util;

import lombok.experimental.UtilityClass;
import org.paurus.taxation.v1.model.TaxationRate;

@UtilityClass
public class TaxationServiceUtil {

    public static double calculatePotentialWinAmount(long playedAmount, double decimalOdd) {
        return playedAmount * decimalOdd;
    }

    public static double calculateTax(double potentialWinAmount, double playedAmount, TaxationRate taxationRate) {
        return switch (taxationRate.getTaxationType()) {
            case GENERAL -> calculateTax(potentialWinAmount, taxationRate);
            case WINNINGS -> calculateTax(potentialWinAmount - playedAmount, taxationRate);
        };
    }

    private static double calculateTax(double taxableAmount, TaxationRate taxationRate) {
        return Math.min(taxableAmount * taxationRate.getTaxRate(), Math.min(taxableAmount, taxationRate.getTaxAmount()));
    }
}
