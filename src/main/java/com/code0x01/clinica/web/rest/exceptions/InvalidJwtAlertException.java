package com.code0x01.clinica.web.rest.exceptions;

import org.springframework.validation.BindingResult;

public class InvalidJwtAlertException extends CustomRuntimeException {

    public InvalidJwtAlertException() {
    }

    public InvalidJwtAlertException(String message) {
        super(message);
    }

    public InvalidJwtAlertException(String key, String value) {
        super(key, value);
    }

    public InvalidJwtAlertException(BindingResult result) {
        super(result);
    }

}
