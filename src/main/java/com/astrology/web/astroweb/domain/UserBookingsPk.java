package com.astrology.web.astroweb.domain;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper=false)
public class UserBookingsPk {
	
	@ManyToOne
	private User user;
	
	@OneToOne
	private Booking booking;

}
