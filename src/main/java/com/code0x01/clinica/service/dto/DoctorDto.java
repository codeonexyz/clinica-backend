package com.code0x01.clinica.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private Long uid;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    private String middleName;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateOfBirth;
    
    private String nationalCode;
    
    @NotBlank
    private String gender;
    
    private String homePhone;
    private String workPhone;
    private String cellMobilePhone;
    
    private String address;
    private String otherDetails;
    
    @NotBlank
    private String specialty;
    
    private String email;
    
    private String role;
    
    @Valid
    private UserDto login;

}
