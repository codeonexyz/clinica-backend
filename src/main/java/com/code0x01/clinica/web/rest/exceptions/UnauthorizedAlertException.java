package com.code0x01.clinica.web.rest.exceptions;

public class UnauthorizedAlertException extends RuntimeException {

    public UnauthorizedAlertException() {
    }

    public UnauthorizedAlertException(String message) {
        super(message);
    }

    public UnauthorizedAlertException(String message, Throwable cause) {
        super(message, cause);
    }

}
