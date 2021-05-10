package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.security.jwt.TokenProvider;
import com.code0x01.clinica.service.UserService;
import com.code0x01.clinica.service.dto.AuthenticationDto;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.service.dto.JwtDto;
import com.code0x01.clinica.service.dto.PasswordDto;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.service.dto.UniversalDto;
import com.code0x01.clinica.service.mapper.DoctorMapper;
import com.code0x01.clinica.service.mapper.PatientMapper;
import com.code0x01.clinica.web.rest.exceptions.AuthenticationAlertException;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import com.code0x01.clinica.web.rest.exceptions.ValidationAlertException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@Slf4j
public class UserResource {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private SmartValidator smartValidator;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private PatientMapper patientMapper;
    
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthenticationDto authenticationDto, BindingResult result) throws Exception {
        if (result.hasErrors()) throw new ValidationAlertException(result);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));
        } catch (BadCredentialsException exc) {
            throw new AuthenticationAlertException("badCredentials", "Incorrect username or password");
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationDto.getUsername());
        return ResponseEntity.ok(new JwtDto(tokenProvider.generateToken(userDetails)));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerDoctor(@RequestBody UniversalDto universalDto, @RequestParam("role") String role) throws Exception {
        BindingResult bindingResult;
        if (("ROLE_" + role).equalsIgnoreCase(AuthoritiesConstants.DOCTOR)) {
            DoctorDto doctorDto = doctorMapper.fromUniversalDto(universalDto);
            bindingResult = new BeanPropertyBindingResult(doctorDto, "");
            ValidationUtils.invokeValidator(smartValidator, doctorDto, bindingResult);
            if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
            doctorDto = userService.save(doctorDto);
            return new ResponseEntity<>(doctorDto, HttpStatus.CREATED);
        } else if (("ROLE_" + role).equalsIgnoreCase(AuthoritiesConstants.PATIENT)) {
            PatientDto patientDto = patientMapper.fromUniversalDto(universalDto);
            bindingResult = new BeanPropertyBindingResult(patientDto, "");
            ValidationUtils.invokeValidator(smartValidator, patientDto, bindingResult);
            if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
            patientDto = userService.save(patientDto);
            return new ResponseEntity<>(patientDto, HttpStatus.CREATED);
        } else {
            throw new BadRequestAlertException(String.format("Role '%s' is defined", role));
        }
    }

}
