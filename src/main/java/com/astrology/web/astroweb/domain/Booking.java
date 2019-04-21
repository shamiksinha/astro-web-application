package com.astrology.web.astroweb.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.SequenceGenerator;

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
	
	@OneToOne
	private BookingType bookingType;
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private boolean allDayEvent;
	private String color;
	private boolean recurring;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="username")
    private User user;

}
