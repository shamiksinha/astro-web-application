package com.astrology.web.astroweb.domain;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/*@Entity
@Table(schema="ASTROLOGY",name="USER_BOOKINGS")
@ToString
@EqualsAndHashCode(callSuper=false)
@AssociationOverrides({

@AssociationOverride(name ="pk.user", joinColumns = @JoinColumn(name ="user_id")),

@AssociationOverride(name ="pk.booking", joinColumns = @JoinColumn(name ="booking_id"))

        })*/
public class UserBookings extends AbstractDomain {
	
	@EmbeddedId
	private UserBookingsPk pk=new UserBookingsPk();
	
	@Transient
	public User getUser() {
		return pk.getUser();
	}
	
	public void setUser(User user) {
		pk.setUser(user);
	}
	
	@Transient
	public Booking getBooking() {
		return pk.getBooking();
	}
	
	public void setBooking(Booking booking) {
		pk.setBooking(booking);
	}
}
