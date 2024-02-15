package org.paurus.taxation.v1.service;

import lombok.RequiredArgsConstructor;
import org.paurus.taxation.v1.db.TraderRepository;
import org.paurus.taxation.v1.model.TaxationRate;
import org.paurus.taxation.v1.model.TaxationRequest;
import org.paurus.taxation.v1.model.TaxationResponse;
import org.paurus.taxation.v1.model.Trader;
import org.paurus.taxation.v1.util.TaxationServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaxationService {

    private final TraderRepository traderRepository;

    public TaxationResponse calculateTaxation(TaxationRequest taxationRequest) {
        // assume the regulation is based on the trader country of registration since the assignment requested the use of data storing
        // in practice we would probably need to use an IP service to figure out from where the request originated
        Optional<Trader> trader = traderRepository.findById(taxationRequest.getTraderId());
        if (trader.isEmpty()) {
            throw new IllegalArgumentException(String.format("Unknown trader for %s", taxationRequest.getTraderId()));
        }

        String traderCountry = trader.get().getCountryCode();
        TaxationRate taxationRate = TaxationRate.getByCountryCode(traderCountry);
        if (taxationRate == null) {
            throw new IllegalArgumentException(String.format("Unknown taxation rate for %s", traderCountry));
        }

        double potentialWinAmount = TaxationServiceUtil.calculatePotentialWinAmount(taxationRequest.getPlayedAmount(), taxationRequest.getDecimalOdd());
        return TaxationResponse.builder()
                .taxRate(taxationRate.getTaxRate())
                .taxAmount(taxationRate.getTaxAmount())
                .possibleReturnAmount(potentialWinAmount)
                // it makes no sense to return the same field twice, would challenge the requirement at work
                .possibleReturnAmountBeforeTax(potentialWinAmount)
                .possibleReturnAmountAfterTax(potentialWinAmount - TaxationServiceUtil.calculateTax(potentialWinAmount, taxationRequest.getPlayedAmount(), taxationRate))
                .build();
    }

}
