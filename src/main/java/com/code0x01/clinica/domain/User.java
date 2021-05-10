package com.code0x01.clinica.domain;

import com.code0x01.clinica.security.AuthoritiesConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Email is required")
    @Email
    @Column(unique = true)
    private String email;
    
    @NotNull(message = "Password is required")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    @Column(nullable = false)
    private Boolean activated;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authority_id")
    private Authority authority;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.activated = true;
        this.createdAt = new Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    
    public Boolean isDoctor() {
        return this.authority.getName().equalsIgnoreCase(AuthoritiesConstants.DOCTOR);
    }
    
    public Boolean isPatient() {
        return this.authority.getName().equalsIgnoreCase(AuthoritiesConstants.PATIENT);
    }
}
