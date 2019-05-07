package com.astrology.web.astroweb.services;

import java.time.LocalDateTime;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.User;

import org.springframework.data.domain.Page;

public interface BookingService extends CRUDService<Booking> {
	
	//Booking findBookingByBookingId(String bookingId);
	Page<com.astrology.web.astroweb.model.Booking> findFutureBookings(int page,int size);
	Page<com.astrology.web.astroweb.model.Booking> findFutureBookingsForUser(int page,int size,User user);
	Booking findByStartTime(LocalDateTime startTime);
}
