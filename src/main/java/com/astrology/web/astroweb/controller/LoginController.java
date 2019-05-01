package com.astrology.web.astroweb.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.astrology.web.astroweb.model.Booking;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.model.User;
import com.astrology.web.astroweb.services.BookingService;
import com.astrology.web.astroweb.services.UserService;
import com.astrology.web.astroweb.util.CommonConstants;
import com.astrology.web.astroweb.util.PageWrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private final Logger logger = LogManager.getLogger(this.getClass());

	private BookingService bookingService;

	private UserService userService;

	/**
	 * @param userService the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	

	/**
	 * @param bookingService the bookingService to set
	 */
	@Autowired
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}
		
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

	@GetMapping("/home/profile")
	public String getBookings(Model model,Authentication authentication,@RequestParam("page") Optional<Integer> page, 
	@RequestParam("size") Optional<Integer> size){
		User user=getUser(authentication);
		int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
		Page<Booking> bookings=null;
		/* bookings=userService.findByUsername(user.getUsername()).getBookings().stream().filter(booking->booking.getStartTime().isAfter(LocalDateTime.now())).map(booking->{
			Booking userBooking=new Booking();
			userBooking.setBookingDesc(booking.getBookingDesc());
			userBooking.setBookingType(booking.getBookingType().getBookingTypeDesc());
			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
			userBooking.setEndTime(booking.getEndTime().format(formatter));
			userBooking.setStartTime(booking.getStartTime().format(formatter));
			return userBooking;
		}).collect(Collectors.toList()); */
		if (user.getAuthorities().stream().filter(grantedAuth->grantedAuth.getAuthority().equals("ADMIN")).count()>0){
			/* bookings=((List<com.astrology.web.astroweb.domain.Booking>)bookingService.listAll()).stream().map(booking->{
				Booking userBooking=new Booking();
				userBooking.setBookingDesc(booking.getBookingDesc());
				userBooking.setBookingType(booking.getBookingType().getBookingTypeDesc());
				DateTimeFormatter formatter=DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");
				userBooking.setEndTime(booking.getEndTime().format(formatter));
				userBooking.setStartTime(booking.getStartTime().format(formatter));
				return userBooking;
			}).collect(Collectors.toList()); */
			bookings=bookingService.findFutureBookings(currentPage-1, pageSize);
		}else{
			bookings=bookingService.findFutureBookingsForUser(currentPage-1, pageSize, userService.findByUsername(user.getUsername()));
		}	
		
		/* model.addAttribute("bookings", bookings);
 
        int totalPages = bookings.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
		} */
		PageWrapper<Booking> bookingPage = new PageWrapper<Booking>(bookings,"/home/profile");
		model.addAttribute("page", bookingPage);
		return "userprofile";
	}

	@GetMapping("/homepage")
	public String getHomePage(){
		return "redirect:home";
	}
}
