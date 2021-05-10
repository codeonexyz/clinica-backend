package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Patient;
import com.code0x01.clinica.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByLogin(User login);

}
