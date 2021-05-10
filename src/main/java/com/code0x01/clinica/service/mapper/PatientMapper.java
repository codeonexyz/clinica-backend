package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.service.dto.UniversalDto;
import com.code0x01.clinica.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PatientMapper {
    
    @Mapping(target = "login", ignore = true)
    Patient toEntity(PatientDto patientDto);
    
    @Mapping(target = "login", ignore = true)
    @Mapping(source = "login.id", target = "uid")
    @Mapping(source = "login.email", target = "email")
    @Mapping(source = "login.authority.name", target = "role")
    PatientDto toDto(Patient patient);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    Patient updateEntity(PatientDto patientDto, @MappingTarget Patient patient);
    
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "login.email", target = "email")
    @Mapping(source = "login.password", target = "password")
    @Mapping(target = "authorityName", constant = AuthoritiesConstants.PATIENT)
    UserDto toUserDto(PatientDto patientDto);
    
    PatientDto fromUniversalDto(UniversalDto universalDto);

}
