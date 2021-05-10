package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
