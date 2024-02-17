package org.paurus.taxation.v1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unknown trader.")
public class UnknownTraderException extends RuntimeException {
}
