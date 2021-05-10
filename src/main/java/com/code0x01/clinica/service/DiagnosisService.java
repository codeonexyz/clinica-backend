package com.code0x01.clinica.service;

import com.code0x01.clinica.domain.Diagnosis;
import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.Drug;
import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.domain.enumeration.VisitStatus;
import com.code0x01.clinica.repository.DiagnosisRepository;
import com.code0x01.clinica.repository.DoctorRepository;
import com.code0x01.clinica.repository.DrugRepository;
import com.code0x01.clinica.repository.PatientRepository;
import com.code0x01.clinica.repository.UserRepository;
import com.code0x01.clinica.repository.VisitRepository;
import com.code0x01.clinica.service.dto.DiagnosisDto;
import com.code0x01.clinica.service.mapper.DiagnosisMapper;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import com.code0x01.clinica.web.rest.exceptions.UnauthorizedAlertException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DiagnosisService {
    
    @Autowired
    private DiagnosisRepository diagnosisRepository;
    
    @Autowired
    private DiagnosisMapper diagnosisMapper;
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private DrugRepository drugRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VisitRepository visitRepository;
    
    /*
    *   ONLY Doctor members can create diagnoses
    */
    public DiagnosisDto save(DiagnosisDto diagnosisDto, String email) {
        Diagnosis diagnosis = diagnosisMapper.toEntity(diagnosisDto);
        User user = userRepository.findByEmail(email);
        
        // Check if the logged user is actully a doctor member
        // otherwise he's not allowed to create a diagnosis
        if (!user.isDoctor()) throw new UnauthorizedAlertException("You are not allowed to create diagnoses. it's a permission given only to doctor members");
        
        // Check if the patient intending (visiting) the doctor
        Doctor doctor = doctorRepository.findByLogin(user).get();
        Patient patient = patientRepository.findById(diagnosisDto.getPatient().getId())
                .orElseThrow(() -> new NotFoundAlertException(String.format("Patient with id '%d' not found", diagnosisDto.getPatient().getId())));
        if (visitRepository.findByDoctorAndPatientAndStatus(doctor, patient, VisitStatus.ACCEPTED) == null)
            throw new NotFoundAlertException("You don't have an active visit with that patient.");
        
        diagnosis.setDoctor(doctor);
        diagnosis.setPatient(patient);
        
        Set<Drug> drugs = diagnosisDto.getDrugs()
                .stream()
                .map(drugDto -> drugRepository.getOne(drugDto.getId()))
                .collect(Collectors.toSet());
        
        diagnosis.setDrugs(drugs);
        
        diagnosis = diagnosisRepository.save(diagnosis);
        return diagnosisMapper.toDto(diagnosis);
    }
    
    /*
    *   ONLY Doctor members can update it's own patients diagnoses
    */
    public DiagnosisDto update(DiagnosisDto diagnosisDto, String email) {
        User user = userRepository.findByEmail(email);
        if (!user.isDoctor()) throw new UnauthorizedAlertException("You are not allowed to update diagnoses. it's a permission given only to doctor members");
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisDto.getId())
                .orElseThrow(() -> new NotFoundAlertException(String.format("Diagnosis with id '%d' not found", diagnosisDto.getId())));
        
        // Check if the patient intending (visiting) the doctor
        Doctor doctor = doctorRepository.findByLogin(user).get();
        Patient patient = diagnosis.getPatient();
        if (visitRepository.findByDoctorAndPatientAndStatus(doctor, patient, VisitStatus.ACCEPTED) == null)
            throw new NotFoundAlertException("You don't have an active visit with this patient.");
        
        if (!diagnosis.getDoctor().equals(doctorRepository.findByLogin(user).get()))
            throw new UnauthorizedAlertException("You are not allowed to update diagnoses. it wasn't created by you.");
        log.info(">>>> Diagnosis: " + diagnosis.getOtherDetails());        
        log.info(">>>> DiagnosisDto: " + diagnosisDto.getOtherDetails());

        diagnosis = diagnosisRepository.save(diagnosisMapper.udpateEntity(diagnosisDto, diagnosis));
        return diagnosisMapper.toDto(diagnosis);
    }
    
    public DiagnosisDto findById(Long id, String email) {
        User user = userRepository.findByEmail(email);
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElseThrow(() -> new NotFoundAlertException(String.format("Diagnosis with id '%d' not found", id)));
        if ((user.isDoctor() && !diagnosis.getDoctor().equals(doctorRepository.findByLogin(user).get())) || 
            (user.isPatient() && !diagnosis.getPatient().equals(patientRepository.findByLogin(user).get()))) {
            throw new UnauthorizedAlertException("You are not allowed to update diagnoses. it wasn't created by you.");
        }
        return diagnosisMapper.toDto(diagnosis);
    }
    
    public List<DiagnosisDto> findAll(String email) {
        User user = userRepository.findByEmail(email);
        if (user.isDoctor()) {
            return diagnosisRepository
                    .findAll()
                    .stream()
                    .filter(diagnosis -> diagnosis.getDoctor().equals(doctorRepository.findByLogin(user).get()))
                    .map(diagnosisMapper::toDto)
                    .collect(Collectors.toList());
        } else if (user.isPatient()) {
            return diagnosisRepository
                    .findAll()
                    .stream()
                    .filter(diagnosis -> diagnosis.getPatient().equals(patientRepository.findByLogin(user).get()))
                    .map(diagnosisMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
    
    public void delete(Long id, String email) {
        User user = userRepository.findByEmail(email);
        Diagnosis diagnosis = diagnosisRepository.findById(id).orElseThrow(() -> new NotFoundAlertException(String.format("Diagnosis with id '%d' not found", id)));
        if ((user.isDoctor() && !diagnosis.getDoctor().equals(doctorRepository.findByLogin(user).get())) || 
            (user.isPatient() && !diagnosis.getPatient().equals(patientRepository.findByLogin(user).get()))) {
            throw new UnauthorizedAlertException("You are not allowed to update diagnoses. it wasn't created by you.");
        }   
        diagnosisRepository.delete(diagnosis);
    }

}
