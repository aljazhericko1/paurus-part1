package org.paurus.taxation.v1.service;

import lombok.RequiredArgsConstructor;
import org.paurus.taxation.v1.db.TraderRepository;
import org.paurus.taxation.v1.model.TaxationRate;
import org.paurus.taxation.v1.model.TaxationRequest;
import org.paurus.taxation.v1.model.TaxationResponse;
import org.paurus.taxation.v1.model.TaxationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaxationService {

    TraderRepository traderRepository;

    public TaxationResponse calculateTaxation(TaxationRequest taxationRequest) {
        // assume the regulation is based on the trader country of registration since the assignment requested the use of data storing
        // in practice we would potentially need to use an IP service to figure out from where the request originated
        String traderCountry = traderRepository.getCountryById(taxationRequest.getTraderId());
        TaxationRate taxationRate = TaxationRate.getByCountryCode(traderCountry);

        double potentialWinAmount = calculateReturnAmount(taxationRequest.getPlayerBetAmount(), taxationRequest.getDecimalOdds());
        return TaxationResponse.builder()
                .taxRate(taxationRate.getTaxRate())
                .taxAmount(taxationRate.getTaxAmount())
                .possibleReturnAmount(potentialWinAmount)
                // it makes no sense to return the same field twice, would challenge the requirement at work
                .possibleReturnAmountBeforeTax(potentialWinAmount)
                .possibleReturnAmountAfterTax(calculatePotentialWinAmountAfterTax(potentialWinAmount, taxationRequest.getPlayerBetAmount(), taxationRate))
                .build();
    }

    private static double calculateReturnAmount(long playerBetAmount, double decimalOdds) {
        return playerBetAmount * decimalOdds;
    }

    private double calculatePotentialWinAmountAfterTax(double potentialWinAmount, double playerBetAmount, TaxationRate taxationRate) {
        return switch (taxationRate.getTaxationType()) {
            case GENERAL -> calculateAfterTaxAmount(potentialWinAmount, taxationRate);
            case WINNINGS -> calculateAfterTaxAmount(potentialWinAmount - playerBetAmount, taxationRate);
        };
    }

    private double calculateAfterTaxAmount(double taxableAmount, TaxationRate taxationRate) {
        return Math.min(taxableAmount * taxationRate.getTaxRate(), Math.max(taxableAmount, taxationRate.getTaxAmount()));
    }

}
