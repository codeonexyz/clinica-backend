package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorityMapper.class})
public interface UserMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(source = "authorityName", target = "authority.name")
    User toEntity(UserDto userDto);

    @Mapping(source = "authority.name", target = "authorityName")
    UserDto toDto(User user);
}
