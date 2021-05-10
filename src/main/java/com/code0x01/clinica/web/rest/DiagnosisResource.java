package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.service.DiagnosisService;
import com.code0x01.clinica.service.dto.DiagnosisDto;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.ValidationAlertException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@Slf4j
public class DiagnosisResource {
    
    @Autowired
    private DiagnosisService diagnosisService;
    
    @PostMapping("/diagnoses")
    public ResponseEntity<DiagnosisDto> createDiagnosis(
            @Valid @RequestBody DiagnosisDto diagnosisDto, 
            BindingResult bindingResult, Principal principal) throws Exception {
        if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
        if (diagnosisDto.getId() != null) throw new BadRequestAlertException("id already exists");
        DiagnosisDto result = diagnosisService.save(diagnosisDto, principal.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @GetMapping("/diagnoses/{id}")
    public ResponseEntity<DiagnosisDto> getDiagnosis(@PathVariable(name = "id") Long id, Principal principal) {
        return ResponseEntity.ok(diagnosisService.findById(id, principal.getName()));
    }
    
    @GetMapping("/diagnoses")
    public ResponseEntity<List<DiagnosisDto>> getAllDiagnoses(Principal principal) {
        return ResponseEntity.ok(diagnosisService.findAll(principal.getName()));
    }
    
    @PutMapping("/diagnoses")
    public ResponseEntity<DiagnosisDto> updateDiagnosis(
            @Valid @RequestBody DiagnosisDto diagnosisDto, 
            BindingResult bindingResult, 
            Principal principal) throws Exception {
        if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
        if (diagnosisDto.getId() == null) throw new BadRequestAlertException("invalid id");
        return ResponseEntity.ok(diagnosisService.update(diagnosisDto, principal.getName()));
    }
    
    @DeleteMapping("/diagnoses/{id}")
    public ResponseEntity<?> deleteDiagnosis(@PathVariable(name = "id") Long id, Principal principal) {
        diagnosisService.delete(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
