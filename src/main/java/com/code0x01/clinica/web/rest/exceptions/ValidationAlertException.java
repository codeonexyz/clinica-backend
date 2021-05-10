package com.code0x01.clinica.web.rest.exceptions;

import org.springframework.validation.BindingResult;

public class ValidationAlertException extends CustomRuntimeException {

    public ValidationAlertException() {
    }

    public ValidationAlertException(String message) {
        super(message);
    }

    public ValidationAlertException(String key, String value) {
        super(key, value);
    }

    public ValidationAlertException(BindingResult result) {
        super(result);
    }

}
