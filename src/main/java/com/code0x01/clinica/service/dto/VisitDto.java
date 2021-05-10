package com.code0x01.clinica.service.dto;

import com.code0x01.clinica.domain.enumeration.VisitStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {
    
    private Long id;
    
    private DoctorDto doctor;
    
    private PatientDto patient;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateFrom;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateTo;
    
    private Float totalCost;
    
    private VisitStatus status;

}
