package com.astrology.web.astroweb.repositories;

import java.time.LocalDateTime;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepository extends PagingAndSortingRepository <Booking, Integer> {
	//Booking findBookingByBookingId(String bookingId);
	Page<Booking> findFirst100ByStartTimeAfter(LocalDateTime now,Pageable pageable);

	Page<Booking> findByUserAndStartTimeAfter(User user,LocalDateTime now,Pageable pageable);

	Booking findByStartTime(LocalDateTime startTime);
}
