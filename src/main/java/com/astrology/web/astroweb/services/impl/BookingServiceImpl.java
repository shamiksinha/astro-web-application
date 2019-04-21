package com.astrology.web.astroweb.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.repositories.BookingRepository;
import com.astrology.web.astroweb.services.BookingService;

@Service("bookingService")
@Profile("springdatajpa")
public class BookingServiceImpl implements BookingService {
	
	private BookingRepository bookingRepository;
	
	@Autowired
	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	@Override
	public List<Booking> listAll() {		
		List<Booking> bookings=new ArrayList<>();
		bookingRepository.findAll().forEach(bookings::add);
		return bookings;
	}

	@Override
	public Booking getById(Integer id) {
		return bookingRepository.findById(id).get();
	}

	@Override
	public Booking saveOrUpdate(Booking domainObject) {
		return bookingRepository.save(domainObject);
	}

	@Override
	public void delete(Integer id) {
		bookingRepository.deleteById(id);
	}

	/*@Override
	public Booking findBookingByBookingId(String bookingId) {
		return bookingRepository.findBookingByBookingId(bookingId);
	}*/

}
