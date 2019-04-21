package com.astrology.web.astroweb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.astrology.web.astroweb.model.Booking;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.model.User;
import com.astrology.web.astroweb.util.CommonConstants;

@Controller
public class LoginController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());
		
	@GetMapping("/home")
	public String login(Model model,Authentication authentication,HttpSession session) {
		//User user=new User();
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//Assert.notNull(authentication, "Authentication object not available in spring context");
		//Object myUser = (authentication != null) ? authentication.getPrincipal() :  null;
		//Assert.notNull(myUser, "Principal not available in spring context");
		//Assert.isTrue((myUser instanceof UserDetails), "Principal not type of UserDetails");
		//Assert.isTrue((myUser instanceof User), "Principal not type of User");
		//if (myUser instanceof UserDetails) {
			//user = (User) myUser;      
		//}
		//Booking booking=new Booking();
		//booking.setBookingDesc("Free Booking");
        //model.addAttribute(CommonConstants.USER_ATTRIBUTE, user);
        //model.addAttribute(CommonConstants.BOOKING_ATTRIBUTE,booking);
        logger.info("User in model home : "+model.asMap().get(CommonConstants.USER_ATTRIBUTE));
        Enumeration<String> attributes = session.getAttributeNames();
        while(attributes.hasMoreElements()) {
        	String attributeName = attributes.nextElement();
        	logger.info("Session Attribute Name: "+attributeName+" Value: "+session.getAttribute(attributeName));
        }
        
        session.setAttribute("userId", getUser(authentication).getUsername());
        return "home";
	}
	
	@ModelAttribute("payment")
	public Payment getPayment(Authentication authentication) {
		User user=getUser(authentication);
		Payment payment = new Payment();
		payment.setNoOfItems(1);
		payment.setEmail(user.getUsername());
		payment.setUser(user);
		payment.setName(user.getFirstName()+" "+user.getLastName());
		payment.setPhone(user.getPhone());
		payment.setTransactionId("TRN"+LocalDateTime.now().getNano());
		payment.setDisabled(false);
		return payment;
	}
	
	@ModelAttribute(CommonConstants.BOOKING_ATTRIBUTE)
	public Booking getBooking() {
		Booking booking=new Booking();
		booking.setBookingDesc("Free Booking");
		booking.setBookingType("free");
		booking.setDisabled(false);
		return booking;
	}

	@ModelAttribute(CommonConstants.USER_ATTRIBUTE)
	public User getUser( Authentication authentication) {
		User user=new User();
		Object myUser = (authentication != null) ? authentication.getPrincipal() :  null;
		if (myUser instanceof UserDetails) {
			user = (User) myUser;      
		}
		return user;
	}
	
	@ModelAttribute("monthName")
	public String getMonthName() {
		logger.info("Month Name is "+LocalDate.now().getMonth().name());
		return LocalDate.now().getMonth().name();
	}
}
