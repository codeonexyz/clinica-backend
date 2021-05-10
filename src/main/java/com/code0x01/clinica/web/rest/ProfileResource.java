package com.code0x01.clinica.web.rest;

import com.code0x01.clinica.service.dto.PasswordDto;
import com.code0x01.clinica.service.ProfileService;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import com.code0x01.clinica.web.rest.exceptions.NotFoundAlertException;
import com.code0x01.clinica.web.rest.exceptions.ValidationAlertException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@Slf4j
public class ProfileResource {
    
    @Autowired
    private ProfileService profileService;
    
    @GetMapping("/profile")
    public ResponseEntity<?> getUser(Principal principal) {
        Optional<?> profile = profileService.findByEmail(principal.getName());
        if (!profile.isPresent()) throw new NotFoundAlertException("Profile not found");
        return ResponseEntity.ok(profile.get());
    }
    
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id, Principal principal) {
        Optional<?> profile = profileService.getProfile(id, principal.getName());
        if (!profile.isPresent()) throw new NotFoundAlertException("Profile not found");
        return ResponseEntity.ok(profile.get());
    }
    
    @GetMapping("/profile/all")
    public ResponseEntity<List<?>> getAllProfiles(@Nullable @RequestParam(name = "type") String type, Principal principal) {
        List<?> profiles = new ArrayList<>();
        if (type == null ) profiles = profileService.findAll();
        else if (type.equalsIgnoreCase("doctors")) profiles = profileService.findAllDoctors();
        else if (type.equalsIgnoreCase("patients")) profiles = profileService.findAllPatients();
        else throw new BadRequestAlertException(String.format("'%s' is not recognized", type));
        return ResponseEntity.ok(profiles);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordDto passwordDto, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) throw new ValidationAlertException(bindingResult);
        profileService.changePassword(passwordDto, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
