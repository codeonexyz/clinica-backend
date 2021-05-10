package com.code0x01.clinica.service;

import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.User;
import com.code0x01.clinica.domain.Visit;
import com.code0x01.clinica.domain.enumeration.VisitStatus;
import com.code0x01.clinica.repository.DoctorRepository;
import com.code0x01.clinica.repository.PatientRepository;
import com.code0x01.clinica.repository.UserRepository;
import com.code0x01.clinica.repository.VisitRepository;
import com.code0x01.clinica.security.AuthoritiesConstants;
import com.code0x01.clinica.service.dto.VisitDto;
import com.code0x01.clinica.service.mapper.VisitMapper;
import com.code0x01.clinica.service.mapper.VisitStatusMapper;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.ForbiddenAlertException;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VisitService {
    
    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private VisitMapper visitMapper;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private VisitRepository visitRepository;
    
    @Autowired
    private VisitStatusMapper visitStatusMapper;
    
    public VisitDto save(VisitDto visitDto, String email) {
        User user = userRepository.findByEmail(email);
        if (!user.getAuthority().getName().equalsIgnoreCase(AuthoritiesConstants.PATIENT)) {
            throw new ForbiddenAlertException("You are not allowed to request a visit, as it's a permission given only to patient members");
        }
        Visit visit = visitMapper.toEntity(visitDto);
        Doctor doctor = doctorRepository.getOne(visitDto.getDoctor().getId());
        Patient patient = patientRepository.findByLogin(user).get();
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }
    
    public List<VisitDto> findAll(String status, String email) {
        User user = userRepository.findByEmail(email);
        VisitStatus visitStatus = visitStatusMapper.fromString(status);
        if (user.isDoctor()) {
            Doctor doctor = doctorRepository.findByLogin(user).get();
            return visitRepository.findAll().stream()
                    .filter(visit -> visit.getDoctor().equals(doctor) && ( visitStatus == null || visit.getStatus().equals(visitStatus) ))
                    .map(visitMapper::toDto)
                    .collect(Collectors.toList());
        } else if (user.isPatient()) {
            Patient patient = patientRepository.findByLogin(user).get();
            return visitRepository.findAll().stream()
                    .filter(visit -> visit.getPatient().equals(patient) && ( visitStatus == null || visit.getStatus().equals(visitStatus) ))
                    .map(visitMapper::toDto)
                    .collect(Collectors.toList());
        }
        return Arrays.asList();
    }
    
    public VisitDto updateStatus(Long id, String status, String email) {
        User user = userRepository.findByEmail(email);
        if (!user.isDoctor()) throw new ForbiddenAlertException("You are not allowed to receive visit requests");
        Doctor doctor = doctorRepository.findByLogin(user).get();
        Visit visit = visitRepository.findByIdAndDoctor(id, doctor);
        if (visit == null) throw new NotFoundAlertException(String.format("Visit not found", id));
        visit.setStatus(visitStatusMapper.fromString(status));
        visit = visitRepository.save(visit);
        return visitMapper.toDto(visit);
    }
    
    public VisitDto update(VisitDto visitDto, String email) {
        Visit visit = visitRepository.findById(visitDto.getId()).orElseThrow(() -> new NotFoundAlertException(String.format("Visit with id '%d' not found", visitDto.getId())));
        User user = userRepository.findByEmail(email);
        if ((user.isDoctor() && visit.getDoctor().getLogin().equals(user)) || (user.isPatient() && visit.getPatient().getLogin().equals(user))) {
            visit = visitMapper.updateEntity(visitDto, visit);
            visit = visitRepository.save(visit);
            return visitMapper.toDto(visit);
        } else {
            throw new NotFoundAlertException(String.format("Visit with id '%d' not found", visitDto.getId()));
        }
    }
    
    public void deleteVisit(Long id, String email) {
        User user = userRepository.findByEmail(email);
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new NotFoundAlertException(String.format("Visit with id '%d' not found", id)));
        if (user.isDoctor() && visit.getDoctor().getLogin().equals(user)) {
            visitRepository.delete(visit);
            return;
        } else if (user.isPatient() && visit.getPatient().getLogin().equals(user)) {
            if (!visit.isAccepted()) {
                visitRepository.delete(visit);
                return;
            } else {
                throw new BadRequestAlertException("You can't delete a visit after it has been accepted by a doctor");
            }
        }
        throw new NotFoundAlertException(String.format("Visit with id '%d' not found", id));
    }

}
