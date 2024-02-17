package org.paurus.taxation.v1.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.paurus.taxation.v1.exception.RequestValidationException;
import org.paurus.taxation.v1.model.TaxationRequest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TaxationValidationUtilTest {

    public static Stream<Arguments> combinationsForShouldThrowExceptionWhenInvalidRequest() {
        return Stream.of(
                Arguments.of(TaxationRequest.builder().build()),
                Arguments.of(TaxationRequest.builder().traderId(1L).decimalOdd(1.).build()),
                Arguments.of(TaxationRequest.builder().traderId(1L).playedAmount(1L).build()),
                Arguments.of(TaxationRequest.builder().decimalOdd(1.).playedAmount(1L).build()),
                Arguments.of(TaxationRequest.builder().traderId(1L).decimalOdd(0.9).playedAmount(1L).build()),
                Arguments.of(TaxationRequest.builder().traderId(1L).decimalOdd(-1.).playedAmount(1L).build()),
                Arguments.of(TaxationRequest.builder().traderId(1L).decimalOdd(1.0).playedAmount(-1L).build())
        );
    }

    @Test
    void shouldNotThrowExceptionWhenValidRequest() {
        // Given
        TaxationRequest taxationRequest = TaxationRequest.builder()
                .traderId(1L)
                .decimalOdd(1.0)
                .playedAmount(1L)
                .build();


        // , When, Then
        assertDoesNotThrow(() -> TaxationValidationUtil.validateTaxationRequest(taxationRequest));
    }

    @ParameterizedTest
    @MethodSource(value = "combinationsForShouldThrowExceptionWhenInvalidRequest")
    void shouldThrowExceptionWhenInvalidRequest(TaxationRequest taxationRequest) {
        // Given, When, Then
        assertThatThrownBy(() -> TaxationValidationUtil.validateTaxationRequest(taxationRequest))
                .isInstanceOf(RequestValidationException.class);
    }

}