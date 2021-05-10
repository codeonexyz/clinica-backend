package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.Visit;
import com.code0x01.clinica.domain.enumeration.VisitStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    
    Visit findByIdAndDoctor(Long id, Doctor doctor);
    
    Visit findByDoctorAndPatientAndStatus(Doctor doctor, Patient Patient, VisitStatus status);
    
}
