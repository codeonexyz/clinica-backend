package com.code0x01.clinica.web.rest.exceptions;

import com.code0x01.clinica.exceptions.ExceptionResponse;
import com.code0x01.clinica.service.mapper.ExceptionResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    
    @Autowired
    private ExceptionResponseMapper exceptionResponseMapper;
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleNotFoundAlertException(NotFoundAlertException exc, WebRequest request) {   
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleAuthenticationAlertException(AuthenticationAlertException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.BAD_REQUEST);
        response.setErrors(exc.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleInternalAuthenticationServiceException(InternalAuthenticationServiceException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.BAD_REQUEST);
        response.addError("badCredentials", "Incorrect username or password");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleValidationAlertException(ValidationAlertException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.BAD_REQUEST);
        response.setErrors(exc.getErrors());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleBadRequestAlertException(BadRequestAlertException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedAlertException(UnauthorizedAlertException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler
    public final ResponseEntity<ExceptionResponse> handleForbiddenAlertException(ForbiddenAlertException exc, WebRequest request) {
        ExceptionResponse response = exceptionResponseMapper.toResponse(exc, request, HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
