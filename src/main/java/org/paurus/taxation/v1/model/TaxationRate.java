package org.paurus.taxation.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum TaxationRate {
    AUSTRIA("AUT", TaxationType.GENERAL, 0.1, 2),
    SLOVENIA("SLO", TaxationType.WINNINGS, 0.2, 1);

    private String alpha3CountryCode;
    private TaxationType taxationType;
    private double taxRate;
    private double taxAmount;

    private static final Map<String, TaxationRate> TAXATION_RATE_PER_COUNTY_CODE = new HashMap<>();

    static {
        for (TaxationRate taxationRate : TaxationRate.values()) {
            if (TAXATION_RATE_PER_COUNTY_CODE.put(taxationRate.getAlpha3CountryCode(), taxationRate) != null) {
                throw new IllegalArgumentException("Duplicate country: " + taxationRate.getAlpha3CountryCode());
            }
        }
    }

    public static TaxationRate getByCountryCode(String alpha3CountryCode) {
        return alpha3CountryCode == null ? null : TAXATION_RATE_PER_COUNTY_CODE.get(alpha3CountryCode.toUpperCase());
    }

}
