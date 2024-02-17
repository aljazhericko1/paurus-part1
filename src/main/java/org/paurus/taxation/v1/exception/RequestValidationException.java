package org.paurus.taxation.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request did not pass the validation.")
public class RequestValidationException extends RuntimeException {
}
