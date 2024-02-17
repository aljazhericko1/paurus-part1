package org.paurus.taxation.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unknown taxation rate for trader's country.")
public class UnknownTaxationRateException extends RuntimeException {
}
