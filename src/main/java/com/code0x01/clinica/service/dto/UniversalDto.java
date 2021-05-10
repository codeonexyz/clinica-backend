package com.code0x01.clinica.service.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversalDto implements Serializable {
    
    private static final Long serialVersionIUD = 1L;

    private Long id;
    
    private Long uid;
    
    private String firstName;
    
    private String lastName;
    private String middleName;

    private Date dateOfBirth;
    
    private String nationalCode;
    
    private String gender;
    
    private String homePhone;
    private String workPhone;
    private String cellMobilePhone;
    
    private Float height;
    private Float weight;
    
    private Boolean insured;
    
    private String address;
    private String otherDetails;
    
    private String specialty;
    
    private String email;
    
    private String role;
   
    private UserDto login;

}
