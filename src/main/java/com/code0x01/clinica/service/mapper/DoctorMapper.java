package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.service.dto.UniversalDto;
import com.code0x01.clinica.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "login", ignore = true)
    Doctor toEntity(DoctorDto doctorDto);
    
    @Mapping(target = "login", ignore = true)
    @Mapping(source = "login.id", target = "uid")
    @Mapping(source = "login.email", target = "email")
    @Mapping(source = "login.authority.name", target = "role")
    DoctorDto toDto(Doctor doctor);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "login", ignore = true)
    Doctor updateEntity(DoctorDto doctorDto, @MappingTarget Doctor doctor);
    
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "login.email", target = "email")
    @Mapping(source = "login.password", target = "password")
    @Mapping(target = "authorityName", constant = AuthoritiesConstants.DOCTOR)
    UserDto toUserDto(DoctorDto doctorDto);
    
    DoctorDto fromUniversalDto(UniversalDto universalDto);
    
}
