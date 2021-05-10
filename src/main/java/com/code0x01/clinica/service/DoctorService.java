package com.code0x01.clinica.service;

import com.code0x01.clinica.repository.DoctorRepository;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.service.mapper.DoctorMapper;
import com.code0x01.clinica.service.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
  
@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    
    public List<DoctorDto> findAll() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDto).collect(Collectors.toList());
    }
    
    public List<DoctorDto> findAllByListOfIds(List<Long> ids) {
        return doctorRepository.findAll().stream().filter(doctor -> ids.contains(doctor.getId())).map(doctorMapper::toDto).collect(Collectors.toList());
    }
    
    public Optional<DoctorDto> findById(Long id) {
        return doctorRepository.findById(id).map(doctorMapper::toDto);
    }

}
