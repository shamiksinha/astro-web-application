package com.astrology.web.astroweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.astrology.web.astroweb.domain.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
	//Booking findBookingByBookingId(String bookingId);

}
