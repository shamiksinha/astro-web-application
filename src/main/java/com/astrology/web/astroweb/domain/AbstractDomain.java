package com.astrology.web.astroweb.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
@Getter
@Setter
public class AbstractDomain implements IDomain {
 
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
    private Integer id;
 
    @Version
    private Integer version;
 
    private Date dateCreated;
    private Date lastUpdated;
 
    
    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated==null) {
            dateCreated = new Date();
        }
    }
}
