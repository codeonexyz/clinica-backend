package com.code0x01.clinica.service.mapper;

import com.code0x01.clinica.domain.enumeration.VisitStatus;
import com.code0x01.clinica.web.rest.exceptions.BadRequestAlertException;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitStatusMapper {
    
    default VisitStatus fromString(String status) {
        VisitStatus visitStatus = null;
        
        if (status == null) {
            return null;
        }
        
        if (status.equalsIgnoreCase("pending")) {
            visitStatus = VisitStatus.PENDING;
        } else if (status.equalsIgnoreCase("accepted")) {
            visitStatus = VisitStatus.ACCEPTED;
        } else if (status.equalsIgnoreCase("rejected")) {
            visitStatus = VisitStatus.REJECTED;
        } else if (status.equalsIgnoreCase("done")) {
            visitStatus = VisitStatus.DONE;
        } else if (status.equalsIgnoreCase("all")) {
            visitStatus = null;
        } else {
            throw new BadRequestAlertException(String.format("the status '%s' is not defined. use one of the following: accepted, rejected, pending", status));
        }
        
        return visitStatus;
    }

}
