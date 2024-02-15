package org.paurus.taxation.v1.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.paurus.taxation.v1.db.TraderRepository;
import org.paurus.taxation.v1.model.TaxationRequest;
import org.paurus.taxation.v1.model.TaxationResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaxationServiceTest {

    @Mock
    private TraderRepository traderRepository;
    @InjectMocks
    private TaxationService taxationService;


    @ParameterizedTest
    @MethodSource(value = "parametersForShouldReturnCorrectTaxConfigurationWhenKnownTaxRate")
    void shouldReturnCorrectTaxConfigurationWhenKnownTaxRate(TaxationRequest request, String traderCountry, TaxationResponse expectedResponse) {
        // Given
        given(traderRepository.getCountryById(anyLong())).willReturn(traderCountry);

        // When
        TaxationResponse taxationResponse = taxationService.calculateTaxation(request);

        // Then
        assertThat(taxationResponse).isEqualTo(expectedResponse);
    }
    public static Stream<Arguments> parametersForShouldReturnCorrectTaxConfigurationWhenKnownTaxRate() {
        return Stream.of(
                // General taxation
                Arguments.of(TaxationRequest.builder().playedAmount(5L).traderId(1L).decimalOdd(1.2).build(), "AUT", TaxationResponse.builder()
                        .taxRate(0.1)
                        .taxAmount(2.)
                        .possibleReturnAmount(6.)
                        .possibleReturnAmountBeforeTax(6.)
                        .possibleReturnAmountAfterTax(5.4)
                        .build()),
                // Winning taxation
                Arguments.of(TaxationRequest.builder().playedAmount(5L).traderId(2L).decimalOdd(1.2).build(), "SLO", TaxationResponse.builder()
                        .taxRate(0.2)
                        .taxAmount(1.)
                        .possibleReturnAmount(6.)
                        .possibleReturnAmountBeforeTax(6.)
                        .possibleReturnAmountAfterTax(5.8)
                        .build())
        );
    }
    @Test
    void shouldThrowExceptionWhenUnknownTraderCountry() {
        // Given
        TaxationRequest taxationRequest = TaxationRequest.builder()
                .traderId(5L)
                .playedAmount(5L)
                .decimalOdd(1.2)
                .build();
        given(traderRepository.getCountryById(5L)).willReturn(null);

        // When, Then
        assertThatThrownBy(() -> taxationService.calculateTaxation(taxationRequest))
                .hasMessageContaining("Unknown trader country");
    }
    @Test
    void shouldThrowExceptionWhenUnknownTaxationRate() {
        // Given
        TaxationRequest taxationRequest = TaxationRequest.builder()
                .traderId(5L)
                .playedAmount(5L)
                .decimalOdd(1.2)
                .build();
        given(traderRepository.getCountryById(5L)).willReturn("UNKNOWN");

        // When, Then
        assertThatThrownBy(() -> taxationService.calculateTaxation(taxationRequest))
                .hasMessageContaining("Unknown taxation rate");
    }

}