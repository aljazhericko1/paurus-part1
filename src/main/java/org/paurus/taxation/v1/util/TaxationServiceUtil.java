package org.paurus.taxation.v1.util;

import lombok.experimental.UtilityClass;
import org.paurus.taxation.v1.model.TaxationRate;

@UtilityClass
public class TaxationServiceUtil {

    public static double calculatePotentialWinAmount(long playedAmount, double decimalOdd) {
        return playedAmount * decimalOdd;
    }

    /**
     * @param potentialWinAmount total amount the player would receive in case of winning
     * @param playedAmount       amount the trader bet
     * @param taxationRate       tax rate based on trader's country
     * @return taxAmount the trader will have to pay in case of winning
     * <p>
     * Calculates the tax the player will have to pay in case of winning. Uses the taxation rate to determine whether to use general or winnings taxation
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
     * @param amountToTax amount based on which the taxation should be done
     * @param taxRate     tax rate used for percentage based calculation
     * @param taxAmount   tax amount for amount based calculation
     * @return taxAmount the trader will have to pay in case of winning
     * <p>
     * The method determines, which tax option is more beneficial for the trader and uses it for calculating the tax amount.
     */
    private static double calculateTax(double amountToTax, Double taxRate, Double taxAmount) {
        double rateTax = amountToTax * taxRate;
        double amountTax = Math.min(amountToTax, taxAmount);
        return Math.min(rateTax, amountTax);
    }
}
