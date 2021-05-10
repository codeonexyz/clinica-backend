package com.code0x01.clinica.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnosis implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient")
    private Patient patient;
    
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "diagnosis_by_doctor")
    private Doctor doctor;
    
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
        name = "diagnosis_drug",
        joinColumns = @JoinColumn(name = "diagnosis_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id")
    )
    private Set<Drug> drugs = new HashSet<>();
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    
    private String otherDetails;
    
    @PrePersist
    public void onUpdate() {
        this.createdAt = new Date();
    }

}
