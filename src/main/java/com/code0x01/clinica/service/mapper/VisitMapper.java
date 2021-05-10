package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Visit;
import com.code0x01.clinica.service.dto.VisitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { DoctorMapper.class, PatientMapper.class })
public interface VisitMapper {
    
    Visit toEntity(VisitDto visitDto);
    
    VisitDto toDto(Visit visit);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "dateFrom", expression = "java( (visitDto.getDateFrom() != null) ? visitDto.getDateFrom() : visit.getDateFrom() )")
    @Mapping(target = "dateTo", expression = "java( (visitDto.getDateTo() != null) ? visitDto.getDateTo() : visit.getDateTo() )")
    @Mapping(target = "totalCost", expression = "java( (visitDto.getTotalCost() != null) ? visitDto.getTotalCost() : visit.getTotalCost() )")
    @Mapping(target = "status", expression = "java( (visitDto.getStatus() != null) ? visitDto.getStatus() : visit.getStatus() )")
    Visit updateEntity(VisitDto visitDto, @MappingTarget Visit visit);

}
