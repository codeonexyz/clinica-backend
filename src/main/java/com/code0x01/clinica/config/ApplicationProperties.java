package com.code0x01.clinica.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreInvalidFields = false)
public class ApplicationProperties {
}
