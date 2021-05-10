package com.code0x01.clinica.security;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class AuthoritiesConstants {
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String DOCTOR = "ROLE_DOCTOR";
    public static final String PATIENT = "ROLE_PATIENT";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
}
