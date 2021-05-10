package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.exceptions.ExceptionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

@Mapper(componentModel = "spring")
public interface ExceptionResponseMapper {

    @Mapping(target = "errors", ignore = true)
    @Mapping(target = "status", expression = "java(httpStatus.value())")
    @Mapping(target = "timestamp", expression = "java(System.currentTimeMillis())")
    @Mapping(source = "exc.message", target = "message")
    @Mapping(target = "details", expression = "java(request.getDescription(false))")
    ExceptionResponse toResponse(Exception exc, WebRequest request, HttpStatus httpStatus);
}
