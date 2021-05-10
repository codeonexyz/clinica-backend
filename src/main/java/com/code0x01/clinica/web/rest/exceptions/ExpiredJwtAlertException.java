package com.code0x01.clinica.web.rest.exceptions;

import org.springframework.validation.BindingResult;

public class ExpiredJwtAlertException extends CustomRuntimeException {

    public ExpiredJwtAlertException() {
    }

    public ExpiredJwtAlertException(String message) {
        super(message);
    }

    public ExpiredJwtAlertException(String key, String value) {
        super(key, value);
    }

    public ExpiredJwtAlertException(BindingResult result) {
        super(result);
    }

}
