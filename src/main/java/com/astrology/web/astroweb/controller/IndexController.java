package com.astrology.web.astroweb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.astrology.web.astroweb.model.Booking;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.model.User;
import com.astrology.web.astroweb.services.UserService;
import com.astrology.web.astroweb.util.CommonConstants;

@Controller
public class IndexController {
	private final Logger logger = LogManager.getLogger(this.getClass());
	
	private UserService userservice;
	
	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}
	
	@GetMapping("/index")
    public String indexForm(Model model) {
		
        return "index";
    }

	@GetMapping("/")
    public String defaultPath(Model model) {
        logger.info("Redirecting to index page");
        return "redirect:index";
    }
	
	@PostMapping("/register")
	public String register(User userDetails){
		logger.info("Register method reached");
		//logger.info("User in model home : "+model.asMap().get(CommonConstants.USER_ATTRIBUTE));
		logger.info("User in post body : "+userDetails);
		com.astrology.web.astroweb.domain.User user = new com.astrology.web.astroweb.domain.User();
		user.setEmail(userDetails.getUsername());
		user.setFirstName(userDetails.getFirstName());
		user.setLastName(userDetails.getLastName());
		user.setPhone(userDetails.getPhone());
		user.setTextPass(userDetails.getPassword());
		user.setUsername(userDetails.getUsername());
		userservice.saveOrUpdate(user);
		return "redirect:index";
	}
	
	@ModelAttribute(CommonConstants.USER_ATTRIBUTE)
	public User getUser( ) {
		User user=new User();
		return user;
	}
	
	@ModelAttribute("monthName")
	public String getMonthName() {
		logger.info("Month Name is "+LocalDate.now().getMonth().name());
		return LocalDate.now().getMonth().name();
	}
	
	@ModelAttribute("payment")
	public Payment getPayment() {
		Payment payment = new Payment();
		payment.setNoOfItems(1);
		payment.setDisabled(true);
		return payment;
	}
	
	@ModelAttribute(CommonConstants.BOOKING_ATTRIBUTE)
	public Booking getBooking() {
		Booking booking=new Booking();
		booking.setBookingDesc("Free Booking");
		booking.setDisabled(true);
		return booking;
	}
}
