package com.astrology.web.astroweb.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.repositories.BookingRepository;
import com.astrology.web.astroweb.services.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
		List<Booking> bookings = new ArrayList<>();
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

	@Override
	public Page<com.astrology.web.astroweb.model.Booking> findFutureBookings(int page,int size) {
		return bookingRepository.findFirst100ByStartTimeAfter(LocalDateTime.now(), PageRequest.of(page, size, Sort.Direction.ASC, "startTime"));
	}

	@Override
	public Page<com.astrology.web.astroweb.model.Booking> findFutureBookingsForUser(int page, int size, User user) {
		return bookingRepository.findByUserAndStartTimeAfter(user, LocalDateTime.now(), PageRequest.of(page, size, Sort.Direction.ASC, "startTime"));
	}

	/*@Override
	public Booking findBookingByBookingId(String bookingId) {
		return bookingRepository.findBookingByBookingId(bookingId);
	}*/

}
