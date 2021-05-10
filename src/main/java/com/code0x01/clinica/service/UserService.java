package com.code0x01.clinica.service;

import com.code0x01.clinica.domain.Authority;
import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.repository.AuthorityRepository;
import com.code0x01.clinica.repository.DoctorRepository;
import com.code0x01.clinica.repository.PatientRepository;
import com.code0x01.clinica.repository.UserRepository;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.service.dto.UserDto;
import com.code0x01.clinica.service.mapper.DoctorMapper;
import com.code0x01.clinica.service.mapper.PatientMapper;
import com.code0x01.clinica.service.mapper.UserMapper;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository; 
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private DoctorMapper doctorMapper;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private PatientMapper patientMapper;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new NotFoundAlertException(String.format("The email '%s' does not exists.", email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), toSimpleGrantedAuthority(user.getAuthority()));
    }
    
    protected Collection<SimpleGrantedAuthority> toSimpleGrantedAuthority(Authority authority) {
        return Arrays.asList(new SimpleGrantedAuthority(authority.getName()));
    }

    protected Collection<SimpleGrantedAuthority> toSimpleGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream().map((Authority authority) -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority.getName());
            return simpleGrantedAuthority;
        }).collect(Collectors.toList());
    }
    
    public User save(UserDto userDto) {
        try {
            User user = userMapper.toEntity(userDto);
            Authority authority = authorityRepository.findByName(userDto.getAuthorityName());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setActivated(true);
            user.setAuthority(authority);
            return userRepository.save(user);
        } catch (RuntimeException exc) {
            throw new BadRequestAlertException(String.format("Email address '%s' already exists.", userDto.getEmail()));
        }
    }
    
    @Transactional
    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);
        User user = save(doctorMapper.toUserDto(doctorDto));
        doctor.setLogin(user);
        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }
    
    @Transactional
    public PatientDto save(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);
        User user = save(patientMapper.toUserDto(patientDto));
        patient.setLogin(user);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

}
