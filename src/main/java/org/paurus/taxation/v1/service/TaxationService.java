package org.paurus.taxation.v1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.paurus.taxation.v1.db.TraderRepository;
import org.paurus.taxation.v1.exception.UnknownTaxationRateException;
import org.paurus.taxation.v1.exception.UnknownTraderException;
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
@Slf4j
public class TaxationService {

    private final TraderRepository traderRepository;

    public TaxationResponse calculateTaxation(TaxationRequest taxationRequest) {
        // assume the regulation is based on the trader country of registration since the assignment requested the use of data storing
        // in practice we would probably need to use an IP service to figure out from where the request originated
        Optional<Trader> trader = traderRepository.findById(taxationRequest.getTraderId());
        if (trader.isEmpty()) {
            log.warn("Unknown trader for id '{}'", taxationRequest.getTraderId());
            throw new UnknownTraderException();
        }

        String traderCountry = trader.get().getCountryCode();
        TaxationRate taxationRate = TaxationRate.getByCountryCode(traderCountry);
        if (taxationRate == null) {
            log.warn("Unknown taxation rate for trader country '{}'", traderCountry);
            throw new UnknownTaxationRateException();
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
