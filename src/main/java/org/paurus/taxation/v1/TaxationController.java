package org.paurus.taxation.v1;

import lombok.RequiredArgsConstructor;
import org.paurus.taxation.v1.model.TaxationRequest;
import org.paurus.taxation.v1.model.TaxationResponse;
import org.paurus.taxation.v1.service.TaxationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.paurus.taxation.v1.util.TaxationValidationUtil.validateTaxationRequest;

@RestController
@RequestMapping(value = "/taxation/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaxationController {

    private final TaxationService taxationService;

    @PostMapping(value = "/taxCalculation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaxationResponse calculateTax(@RequestBody TaxationRequest taxationRequest) {
        validateTaxationRequest(taxationRequest);
        return taxationService.calculateTaxation(taxationRequest);
    }

}
