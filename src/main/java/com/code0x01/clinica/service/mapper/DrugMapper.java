package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Drug;
import com.code0x01.clinica.service.dto.DrugDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DrugMapper {

    Drug toEntity(DrugDto drugDto);
    
    DrugDto toDto(Drug drug);
    
}
