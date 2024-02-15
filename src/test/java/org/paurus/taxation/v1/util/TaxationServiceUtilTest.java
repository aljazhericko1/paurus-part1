package org.paurus.taxation.v1.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.paurus.taxation.v1.model.TaxationRate;
import org.paurus.taxation.v1.model.TaxationType;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class TaxationServiceUtilTest {

    @ParameterizedTest
    @MethodSource(value = "combinationsForShouldReturnPotentialWinAmount")
    void shouldReturnPotentialWinAmount(Long playedBetAmount, Double decimalOdd, Double expectedResult) {
        // Given, When
        double calculatePotentialWinAmount = TaxationServiceUtil.calculatePotentialWinAmount(playedBetAmount, decimalOdd);

        // Then
        assertThat(calculatePotentialWinAmount)
                .isEqualTo(expectedResult);
    }

    public static Stream<Arguments> combinationsForShouldReturnPotentialWinAmount() {
        return Stream.of(
                Arguments.of(5L, 1.2, 6.),
                Arguments.of(5L, 1.1, 5.5),
                Arguments.of(10L, 1.1, 11.)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "combinationsForShouldCalculateTax")
    void shouldCalculateTax(TaxationType taxationType, double taxRate, double taxAmount, double expectedResult) {
        // Given
        TaxationRate taxationRate = Mockito.mock(TaxationRate.class);
        given(taxationRate.getTaxationType()).willReturn(taxationType);
        given(taxationRate.getTaxRate()).willReturn(taxRate);
        given(taxationRate.getTaxAmount()).willReturn(taxAmount);


        // When
        double calculatePotentialWinAmount = TaxationServiceUtil.calculateTax(5., 4., taxationRate);

        // Then
        assertThat(calculatePotentialWinAmount)
                .isEqualTo(expectedResult);
    }

    public static Stream<Arguments> combinationsForShouldCalculateTax() {
        return Stream.of(
                //  General taxation
                // use tax rate as tax amount based would be higher (0.5 < 1)
                Arguments.of(TaxationType.GENERAL, 0.1, 1., 0.5),
                // use tax amount as tax rate based would be higher (0.3 < 0.5)
                Arguments.of(TaxationType.GENERAL, 0.1, 0.3, 0.3),

                // Winnings taxation
                // use tax rate as tax amount based would be higher (0.1 < 0.3)
                Arguments.of(TaxationType.WINNINGS, 0.1, 0.3, 0.1),
                // use tax amount as tax rate based would be higher (0.03 < 0.3)
                Arguments.of(TaxationType.WINNINGS, 0.1, 0.03, 0.03)
        );
    }
}