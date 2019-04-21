package com.astrology.web.astroweb.services.impl;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.astrology.web.astroweb.domain.Booking;
import com.astrology.web.astroweb.domain.PaymentOrder;
import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.repositories.UserRepository;
import com.astrology.web.astroweb.services.EncryptionService;
import com.astrology.web.astroweb.services.UserService;

@Service("userService")
@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private EncryptionService encryptionService;

	@Autowired
	public void setEncryptionService(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
	}
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<?> listAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add); // fun with Java 8
		return users;
	}

	@Override
	public User getById(Integer id) {
		return userRepository.findById(id).get();
	}

	@Override
	public User saveOrUpdate(User domainObject) {
		if (domainObject.getTextPass() != null) {
			domainObject.setPassword(passwordEncoder.encode(domainObject.getTextPass()));
		}
		return userRepository.save(domainObject);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	@Transactional
	public void deleteAll(Iterable<? extends User> users) {
		userRepository.deleteAll(users);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public Iterable<User> saveAll(Iterable<User> users) {
		for (Iterator<User> itr = users.iterator();itr.hasNext();) {
			User domainObject=itr.next();
			if (domainObject.getTextPass() != null) {
				domainObject.setPassword(passwordEncoder.encode(domainObject.getTextPass()));
			}
		}
		return userRepository.saveAll(users);
	}

	@Override
	public Iterable<Booking> findBookingsByUsername(String username) {
		List<Booking> bookings=new ArrayList<>();
		Iterable<Booking> bookingList = userRepository.findByUsername(username).getBookings();
		for (Iterator<Booking> itr = bookingList.iterator();itr.hasNext();) {
			Booking booking = itr.next();
			bookings.add(booking);
		}
		return Collections.unmodifiableList(bookings);
	}

	@Override
	public User saveBooking(Booking booking, String username) {
		User user = findByUsername(username);
		List<Booking> bookingList = (List<Booking>) user.getBookings();
		bookingList.add(booking);
		user=userRepository.save(user);
		return user;
	}

	@Override
	public User savePaymentOrder(Payment payment) {
		User user = findByUsername(payment.getUser().getUsername());
		List<PaymentOrder> paymentOrderList = user.getPaymentOrders();
		PaymentOrder paymentOrder=new PaymentOrder();
		paymentOrder.setAmount(payment.getAmount());
		paymentOrder.setCurrencyCode(payment.getCurrencyCode());
		paymentOrder.setDescription(payment.getDescription());
		paymentOrder.setEmail(payment.getEmail());
		paymentOrder.setName(payment.getName());
		paymentOrder.setPaymentStatus(payment.getPaymentStatus());
		paymentOrder.setPhone(payment.getPhone());
		paymentOrder.setTransactionId(payment.getTransactionId());
		paymentOrder.setUser(user);
		paymentOrderList.add(paymentOrder);		
		return userRepository.save(user);
	}
	
	@Override
	public User updatePaymentOrder(Payment payment) {
		User user = findByUsername(payment.getUser().getUsername());
		PaymentOrder paymentOrderFound = user.getPaymentOrders().stream().filter(paymentOrder->payment.getTransactionId().equals(paymentOrder.getTransactionId())).findFirst().orElse(null);
		if (paymentOrderFound!=null) {
			paymentOrderFound.setPaymentId(payment.getPaymentId());
			paymentOrderFound.setPaymentStatus(payment.getPaymentStatus());
			paymentOrderFound.setUniqueId(payment.getUniqueId());
			user=userRepository.save(user);
		}
		return user;
	}
	
}
