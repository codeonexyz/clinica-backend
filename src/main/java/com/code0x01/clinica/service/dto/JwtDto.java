package com.code0x01.clinica.service.dto;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class JwtDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String jwt;
}
