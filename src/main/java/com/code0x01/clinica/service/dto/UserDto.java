package com.code0x01.clinica.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDto implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank 
    @Email
    private String email;

    @NotBlank 
    @Size(min = 6, max = 255)
    private String password;

    private Boolean activated;
    
    private String authorityName;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;
}
