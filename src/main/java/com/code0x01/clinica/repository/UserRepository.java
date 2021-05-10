package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByEmail(String email);
    User getById(Long id);

}
