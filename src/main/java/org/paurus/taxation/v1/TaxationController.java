package org.paurus.taxation.v1;

import lombok.RequiredArgsConstructor;
import org.paurus.taxation.v1.model.TaxationRequest;
import org.paurus.taxation.v1.model.TaxationResponse;
import org.paurus.taxation.v1.service.TaxationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/taxation/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaxationController {

    private final TaxationService taxationService;

    @RequestMapping(value = "/taxCalculation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaxationResponse getTaxation(TaxationRequest taxationRequest) {
        return taxationService.calculateTaxation(taxationRequest);
    }

}
