package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.service.PatientService;
import com.code0x01.clinica.service.dto.PatientDto;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Slf4j
public class PatientResource {
    
    private final PatientService patientService;
    
    @GetMapping("/patients/")
    public ResponseEntity<List<PatientDto>> getAllPatients(@Nullable @RequestParam("in") List<Long> ids) {
        List<PatientDto> result;
        if (ids != null) result = patientService.findAllByListOfIds(ids);
        else result = patientService.findAll();
        return ResponseEntity.ok(result);
    } 
    
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDto> getPatient(@PathVariable("id") Long id) throws Exception {
        Optional<PatientDto> result = patientService.findById(id);
        if (!result.isPresent()) throw new NotFoundAlertException(String.format("Patient with id '%d' not found", id));
        return ResponseEntity.ok(result.get());
    }
    
}
