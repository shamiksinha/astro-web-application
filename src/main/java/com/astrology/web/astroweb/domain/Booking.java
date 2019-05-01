package com.astrology.web.astroweb.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema="ASTROLOGY")
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
@SequenceGenerator(name="sequence",sequenceName="BOOKING_SEQ")
public class Booking extends AbstractDomain {
	
	/*@Column(unique=true)
	private String bookingId;*/
	private String bookingDesc;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="bookingTypeId", referencedColumnName = "bookingTypeId")
	private BookingType bookingType;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean allDayEvent;
	private String color;
	private boolean recurring;
	
	@OneToOne(mappedBy = "booking",fetch = FetchType.LAZY,optional = true,cascade = CascadeType.ALL,orphanRemoval = true)
	@JoinColumn(name="TRANSACTION_ID", referencedColumnName ="TRANSACTION_ID")
	private PaymentOrder paymentOrder;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="username")
    private User user;

}
