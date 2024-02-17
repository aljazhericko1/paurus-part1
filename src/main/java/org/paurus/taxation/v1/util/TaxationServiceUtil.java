package org.paurus.taxation.v1.util;

import lombok.experimental.UtilityClass;
import org.paurus.taxation.v1.model.TaxationRate;

@UtilityClass
public class TaxationServiceUtil {

    public static double calculatePotentialWinAmount(long playedAmount, double decimalOdd) {
        return playedAmount * decimalOdd;
    }

    /**
     * Calculates the tax the player will have to pay in case of winning. Uses the taxation rate to determine whether to use general or winnings taxation.
     */
    public static double calculateTax(double potentialWinAmount, double playedAmount, TaxationRate taxationRate) {
        return switch (taxationRate.getTaxationType()) {
            case GENERAL -> calculateTax(potentialWinAmount, taxationRate.getTaxRate(), taxationRate.getTaxAmount());
            case WINNINGS -> {
                double taxableAmount = potentialWinAmount - playedAmount;
                yield calculateTax(taxableAmount, taxationRate.getTaxRate(), taxationRate.getTaxAmount());
            }
        };
    }

    /**
     * Determines which tax option is more beneficial for the trader between using tax rate and tax amount and uses it for calculating the tax.
     */
    private static double calculateTax(double amountToTax, Double taxRate, Double taxAmount) {
        double rateTax = amountToTax * taxRate;
        double amountTax = Math.min(amountToTax, taxAmount);
        return Math.min(rateTax, amountTax);
    }
}
