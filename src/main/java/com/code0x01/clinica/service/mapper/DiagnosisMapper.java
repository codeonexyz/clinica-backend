package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Diagnosis;
import com.code0x01.clinica.service.dto.DiagnosisDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PatientMapper.class, DoctorMapper.class, DrugMapper.class})
public interface DiagnosisMapper {
    
    Diagnosis toEntity(DiagnosisDto diagnosisDto);
    
    DiagnosisDto toDto(Diagnosis diagnosis);
    
    default Diagnosis udpateEntity(DiagnosisDto diagnosisDto, @MappingTarget Diagnosis diagnosis) {
        if (diagnosisDto == null || diagnosis == null) {
            return null;
        }
        
        if ((diagnosis.getOtherDetails() == null && diagnosisDto.getOtherDetails() != null) || 
            (!diagnosis.getOtherDetails().equalsIgnoreCase(diagnosisDto.getOtherDetails()))) {
            diagnosis.setOtherDetails(diagnosisDto.getOtherDetails());
        }
        
        return diagnosis;
    }

}
