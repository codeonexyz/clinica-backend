package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Doctor;
import com.code0x01.clinica.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
    Optional<Doctor> findByLogin(User login);

}
