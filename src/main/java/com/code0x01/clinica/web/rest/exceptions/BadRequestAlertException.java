package com.code0x01.clinica.web.rest.exceptions;

public class BadRequestAlertException extends RuntimeException {

    public BadRequestAlertException() {
    }

    public BadRequestAlertException(String message) {
        super(message);
    }

    public BadRequestAlertException(String message, Throwable cause) {
        super(message, cause);
    }

}
