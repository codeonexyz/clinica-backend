package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Diagnosis;
import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.Patient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    
    List<Diagnosis> findAllByPatient(Patient patient);
    
    List<Diagnosis> findAllByDoctor(Doctor doctor);

}
