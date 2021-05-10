package com.code0x01.clinica.web.rest.exceptions;

public class ForbiddenAlertException extends RuntimeException {

    public ForbiddenAlertException() {
    }

    public ForbiddenAlertException(String message) {
        super(message);
    }

    public ForbiddenAlertException(String message, Throwable cause) {
        super(message, cause);
    }

}
