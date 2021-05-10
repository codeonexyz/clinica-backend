package com.code0x01.clinica.service;

import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.repository.DoctorRepository;
import com.code0x01.clinica.repository.PatientRepository;
import com.code0x01.clinica.repository.UserRepository;
import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.service.dto.PasswordDto;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.service.mapper.DoctorMapper;
import com.code0x01.clinica.service.mapper.PatientMapper;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private PatientMapper patientMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    public Optional<?> fromUser(User user) {
        switch (user.getAuthority().getName()) {
            case AuthoritiesConstants.DOCTOR:
                return doctorRepository.findByLogin(user).map(doctorMapper::toDto);
                
            case AuthoritiesConstants.PATIENT:
                return patientRepository.findByLogin(user).map(patientMapper::toDto);
                
            default:
                return null;
        }
    }
    
    public Optional<?> findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return fromUser(user);
    }
    
    public Optional<?> getProfile(Long id, String email) {
        User requestedUser = userRepository.findById(id).orElseThrow(() -> new NotFoundAlertException(String.format("User with id '%d' not found", id)));
        // User requestBy = userRepository.findByEmail(email);

        return fromUser(requestedUser);
    }
    
    public List<?> findAll() {
        return userRepository.findAll().stream().map(user -> fromUser(user).get()).collect(Collectors.toList());
    }
    
    public List<DoctorDto> findAllDoctors() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDto).collect(Collectors.toList());
    }
    
    public List<PatientDto> findAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper::toDto).collect(Collectors.toList());
    }
    
    public void changePassword(PasswordDto passwordDto, String email) {
        User user = userRepository.findByEmail(email);
        user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        userRepository.save(user);
    }

}
