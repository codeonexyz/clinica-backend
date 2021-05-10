package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.service.DoctorService;
import com.code0x01.clinica.service.dto.DoctorDto;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class DoctorResource {

    @Autowired
    private DoctorService doctorService;
    
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors(@Nullable @RequestParam("in") List<Long> ids) {
        List<DoctorDto> result;
        if (ids != null) result = doctorService.findAllByListOfIds(ids);
        else result = doctorService.findAll();
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/doctors/{id}")
    public ResponseEntity<DoctorDto> getDoctor(@PathVariable("id") Long id) throws Exception {
        Optional<DoctorDto> result = doctorService.findById(id);
        if (!result.isPresent()) throw new NotFoundAlertException(String.format("Doctor with id '%d' not found", id));
        return ResponseEntity.ok(result.get());
    }
    
}
