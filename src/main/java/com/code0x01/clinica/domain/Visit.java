package com.code0x01.clinica.domain;

import com.code0x01.clinica.domain.enumeration.VisitStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Visit implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) 
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "patient_id")
    private Patient patient;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateFrom;
    
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dateTo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VisitStatus status;
    
    private Float totalCost;
    
    @PrePersist
    public void onCreate() {
        this.status = VisitStatus.PENDING;
    }
    
    public Boolean isAccepted() {
        return this.status.equals(VisitStatus.ACCEPTED);
    }
    
    public Boolean isRejected() {
        return this.status.equals(VisitStatus.REJECTED);
    }
    
    public Boolean isPending() {
        return this.status.equals(VisitStatus.PENDING);
    }

}
