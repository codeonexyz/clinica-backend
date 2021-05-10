package com.code0x01.clinica.service;

import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.repository.PatientRepository;
import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.service.mapper.PatientMapper;
import com.code0x01.clinica.service.mapper.UserMapper;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {
    
    private final PatientRepository patientRepository;
    
    private final UserMapper userMapper;
    
    private final PatientMapper patientMapper;
    
    private final UserService userService;
    
    public List<PatientDto> findAll() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).collect(Collectors.toList());
    }
    
    public List<PatientDto> findAllByListOfIds(List<Long> ids) {
        return patientRepository.findAll().stream().filter(patient -> ids.contains(patient.getId())).map(patientMapper::toDto).collect(Collectors.toList());
    }
    
    public Optional<PatientDto> findById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

}
