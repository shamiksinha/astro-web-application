package com.astrology.web.astroweb.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;

@Entity
@Table(schema="ASTROLOGY")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@SequenceGenerator(name="sequence",sequenceName="USER_SEQ")
public class User extends AbstractDomain {
	
    private String firstName;
    private String lastName;
    private String email;
    private String password;    
    private final String role="USER";
    
    @Transient
    private String textPass;
    
    private String username;
    
    @NotNull
    @Column(nullable=false)
    private String phone;
    
    //private String bookingId;
    
    /*@ManyToMany(fetch = FetchType.EAGER,cascade= {CascadeType.ALL})
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(schema="ASTROLOGY")
    private List<Product> products= new ArrayList<>();*/
    
    @OneToMany(fetch = FetchType.EAGER,cascade= {CascadeType.ALL},mappedBy="user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Product> products= new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER,cascade= {CascadeType.ALL},mappedBy="user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Booking> bookings= new ArrayList<>();
    
    @OneToMany(fetch = FetchType.EAGER,cascade= {CascadeType.ALL},mappedBy="user")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PaymentOrder> paymentOrders= new ArrayList<>();
}
