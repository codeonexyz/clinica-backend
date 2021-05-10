package com.code0x01.clinica.web.rest.exceptions;

public class NotFoundAlertException extends CustomRuntimeException {

    public NotFoundAlertException() {
    }

    public NotFoundAlertException(String message) {
        super(message);
    }

    public NotFoundAlertException(String key, String value) {
        super(key, value);
    }

}
