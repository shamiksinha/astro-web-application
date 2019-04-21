package com.astrology.web.astroweb.services;

import java.util.List;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.model.Payment;

public interface UserService extends CRUDService<User> {
	 
    User findByUsername(String username);
    
    void deleteAll();
    
    void deleteAll(Iterable<? extends User> users);

	long count();
	
	Iterable<User> saveAll(Iterable<User> users);
	
	Iterable<Booking> findBookingsByUsername(String username);
	
	User saveBooking(Booking booking,String username);

	User savePaymentOrder(Payment payment);

	User updatePaymentOrder(Payment payment);
 
}
