package com.code0x01.clinica.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthenticationDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @NotNull
    @Size(min = 4, max = 50)
    private String password;
}
