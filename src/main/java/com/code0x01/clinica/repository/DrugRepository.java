package com.code0x01.clinica.repository;

import com.code0x01.clinica.domain.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
    
    Drug findByName(String name);

}
