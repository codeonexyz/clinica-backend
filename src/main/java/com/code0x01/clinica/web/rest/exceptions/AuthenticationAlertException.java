package com.code0x01.clinica.web.rest.exceptions;

public class AuthenticationAlertException extends CustomRuntimeException {

    public AuthenticationAlertException() {
    }

    public AuthenticationAlertException(String message) {
        super(message);
    }

    public AuthenticationAlertException(String key, String value) {
        super(key, value);
    }

}
