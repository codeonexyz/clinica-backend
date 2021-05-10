package com.code0x01.clinica.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
    
    @NotBlank 
    @Size(min = 6, max = 255)
    private String newPassword;

}
