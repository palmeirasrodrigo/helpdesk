package com.rodrigo.helpdesk.service.exceptions;

import java.io.Serial;

public class DataIntegrityViolationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1347917164535535964L;

    public DataIntegrityViolationException(String message) {
        super(message);
    }

    public DataIntegrityViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
