package com.code0x01.clinica;

import com.code0x01.clinica.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableSwagger2
public class ClinicaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaApplication.class, args);
    }

}
