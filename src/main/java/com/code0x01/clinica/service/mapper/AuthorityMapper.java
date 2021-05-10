package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.Authority;
import com.code0x01.clinica.service.dto.AuthorityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

    Authority toEntity(AuthorityDto authorityDto);
    
    AuthorityDto toDto(Authority authority);

}
