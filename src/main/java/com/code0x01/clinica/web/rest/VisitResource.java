package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.service.VisitService;
import com.code0x01.clinica.service.dto.VisitDto;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.ValidationAlertException;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class VisitResource {
    
    @Autowired
    private VisitService visitService;
    
    @PostMapping("/visits")
    public ResponseEntity<VisitDto> createVisit(@Valid @RequestBody VisitDto visitDto, BindingResult bindingResult, Principal principal) throws Exception {
        if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
        if (visitDto.getId() != null) throw new BadRequestAlertException("id already exist");
        VisitDto result = visitService.save(visitDto, principal.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PutMapping("/visits")
    public ResponseEntity<VisitDto> updateVisit(@Valid @RequestBody VisitDto visitDto, BindingResult bindingResult, Principal principal) throws Exception {
        if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
        if (visitDto.getId() == null) throw new BadRequestAlertException("invalid id");
        VisitDto result = visitService.update(visitDto, principal.getName());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    
    @PutMapping("/visits/{id}")
    public ResponseEntity<VisitDto> updateVisitStatus(@PathVariable(name = "id") Long id, @RequestParam(name = "status") String status, Principal principal) {
        VisitDto result = visitService.updateStatus(id, status, principal.getName());
        return ResponseEntity.ok(result);
    }
    
    @DeleteMapping("/visits/{id}")
    public ResponseEntity<?> deleteVisit(@PathVariable(name = "id") Long id, Principal principal) {
        visitService.deleteVisit(id, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping("/visits")
    public ResponseEntity<List<VisitDto>> getAllVisits(@Nullable @RequestParam(name = "status") String status, Principal principal) {
        List<VisitDto> result = visitService.findAll(status, principal.getName());
        return ResponseEntity.ok(result);
    }

}
