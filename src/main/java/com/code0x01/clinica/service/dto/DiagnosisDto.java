package com.code0x01.clinica.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisDto {
    
    private Long id;
    
    private PatientDto patient;
    
    private DoctorDto doctor;
    
    private Set<DrugDto> drugs = new HashSet<>();
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    
    private String otherDetails;

}
