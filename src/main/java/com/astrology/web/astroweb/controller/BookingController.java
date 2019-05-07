package com.astrology.web.astroweb.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.astrology.web.astroweb.model.Booking;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.model.User;
import com.astrology.web.astroweb.services.BookingService;
import com.astrology.web.astroweb.services.BookingTypeService;
import com.astrology.web.astroweb.services.UserService;
import com.astrology.web.astroweb.util.CommonConstants;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookingController {
	private final Logger logger = LogManager.getLogger(this.getClass());

	private Instamojo instamojo;

	@Autowired
	public void setInstamojo(Instamojo instamojo) {
		this.instamojo = instamojo;
	}

	private UserService userservice;

	@Autowired
	public void setUserservice(UserService userservice) {
		this.userservice = userservice;
	}

	private BookingTypeService bookingTypeService;

	private BookingService bookingService;

	
	/**
	 * @param bookingTypeService the bookingTypeService to set
	 */
	@Autowired
	public void setBookingTypeService(BookingTypeService bookingTypeService) {
		this.bookingTypeService = bookingTypeService;
	}

	/**
	 * @param bookingService the bookingService to set
	 */
	@Autowired
	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@GetMapping("/booking")
	public String bookingPage(Model model, Authentication authentication) {
		User user = new User();
		Object myUser = (authentication != null) ? authentication.getPrincipal() : null;
		if (myUser instanceof UserDetails) {
			user = (User) myUser;
		}
		model.addAttribute("bookings", userservice.findBookingsByUsername(user.getUsername()));
		model.addAttribute("newBooking", new Booking());
		logger.info("User in BookingController " + user);
		logger.info("Bookings associated with " + user.getUsername() + " is " + model.asMap().get("bookings"));

		return "booking";
	}

	@PostMapping("booking")
	public String createBookings(Model model,Booking bookingModel, Authentication authentication, HttpServletRequest request,RedirectAttributes rdAttributes) {

		logger.info("In getBookings method with booking data value " + bookingModel);
		/*
		 * if (method=="list") { User user=new User(); Object myUser = (authentication
		 * != null) ? authentication.getPrincipal() : null; if (myUser instanceof
		 * UserDetails) { user = (User) myUser; } model.addAttribute("bookings",
		 * userservice.findBookingsByUsername(user.getUsername()));
		 * logger.info("User in BookingController "+user);
		 * logger.info("Bookings associated with "+user.getUsername()+" is "+model.asMap
		 * ().get("bookings")); }
		 */
		// if (method=="addBooking") {
		String url = "paymentComplete";
		String redirectUrlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/";
		com.astrology.web.astroweb.domain.Booking booking = new com.astrology.web.astroweb.domain.Booking();
		// Booking bookingModel=(Booking)
		// model.asMap().get(CommonConstants.BOOKING_ATTRIBUTE);
		booking.setAllDayEvent(bookingModel.isAllDayEvent());
		booking.setBookingDesc(bookingModel.getBookingDesc());
		// booking.setBookingTypeId();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
		booking.setEndTime(LocalDateTime.parse(bookingModel.getEndTime(), formatter));
		booking.setRecurring(bookingModel.isRecurring());
		booking.setStartTime(LocalDateTime.parse(bookingModel.getStartTime(), formatter));
		booking.setBookingType(bookingTypeService.findByTypeDesc(bookingModel.getBookingType()));

		/*
		 * Random rand=new Random(); booking.setBookingId("FC_"+rand.nextInt()); while
		 * (bookingService.findBookingByBookingId(booking.getBookingId())!=null) {
		 * booking.setBookingId("FC_"+rand.nextInt()); }
		 */
		User user = new User();
		Object myUser = (authentication != null) ? authentication.getPrincipal() : null;
		if (myUser instanceof UserDetails) {
			user = (User) myUser;
		}
		boolean existingBookings=false;
		if (bookingModel.getBookingType().equals("paid")) {
			// Save payment details in database
			bookingModel.getPayment().setAmount(100d);
			bookingModel.getPayment().setDescription(bookingModel.getBookingDesc());
			com.astrology.web.astroweb.domain.PaymentOrder paymentOrder = new com.astrology.web.astroweb.domain.PaymentOrder();
			Payment payment = bookingModel.getPayment();
			paymentOrder.setName(payment.getName());
			paymentOrder.setEmail(payment.getEmail());
			paymentOrder.setPhone(payment.getPhone());
			paymentOrder.setCurrencyCode(payment.getCurrencyCode());
			paymentOrder.setAmount(payment.getAmount());
			paymentOrder.setDescription(payment.getDescription());
			paymentOrder.setTransactionId(payment.getTransactionId());
			paymentOrder.setBooking(booking);
			paymentOrder.setUser(userservice.findByUsername(user.getUsername()));
			booking.setPaymentOrder(paymentOrder);
		}else{
			if (bookingService.findByStartTime(booking.getStartTime()) == null){
			url="userconfirmation";
			rdAttributes.addAttribute("msg","Successfully Booked");
			rdAttributes.addAttribute("paymentStatus","null");
			rdAttributes.addAttribute("status", true);
			}else{
				existingBookings=true;
				url="userconfirmation";
				rdAttributes.addAttribute("msg","Slot already booked. Please retry for a different one");
				rdAttributes.addAttribute("paymentStatus","null");
				rdAttributes.addAttribute("status", false);
			}
		}
		booking.setUser(userservice.findByUsername(user.getUsername()));
		logger.info("Payment Order to be created " + booking.getPaymentOrder());
		logger.info("Booking to be created " + booking);
		logger.info("Booking to be created under user " + user);
		com.astrology.web.astroweb.domain.User userDomain=null;
		if (!existingBookings){
			userDomain = userservice.saveBooking(booking, user.getUsername());
		}
		
		// }

		if (userDomain != null) {
			if (bookingModel.getBookingType().equals("paid")) {
				// bookingModel.getPayment().setAmount(100d);
				Payment payment = bookingModel.getPayment();
				// userDomain = userservice.savePaymentOrder(payment);
				// if (user != null) {
				PaymentOrder order = new PaymentOrder();
				order.setName(payment.getName());
				order.setEmail(payment.getEmail());
				order.setPhone(payment.getPhone());
				order.setCurrency(payment.getCurrencyCode());
				order.setAmount(payment.getAmount());
				order.setDescription(payment.getDescription());
				order.setRedirectUrl(redirectUrlPrefix + url);
				// order.setWebhookUrl("http://localhost:8080/home");
				order.setTransactionId(payment.getTransactionId());

				try {
					PaymentOrderResponse paymentOrderResponse = instamojo.createPaymentOrder(order);
					logger.info(paymentOrderResponse.getPaymentOrder().getStatus());
					logger.info("PaymentOrderResponse " + paymentOrderResponse);
					url = paymentOrderResponse.getPaymentOptions().getPaymentUrl();
					logger.info("Redirect URL " + url);

				} catch (HTTPException e) {
					logger.error(e.getStatusCode());
					logger.error(e.getMessage());
					logger.error(e.getJsonPayload());
					model.addAttribute("error",e.getStatusCode()+" "+e.getMessage());

				} catch (ConnectionException e) {
					logger.error(e.getMessage());
					model.addAttribute("error",e.getMessage());	
				}
				// }
			}
		}
		return "redirect:" + url;
	}

	@GetMapping("/edit")
	public String getEditPage(Model model, Authentication authentication, String event) {
		User user = new User();
		Object myUser = (authentication != null) ? authentication.getPrincipal() : null;
		if (myUser instanceof UserDetails) {
			user = (User) myUser;
		}
		model.addAttribute("bookings", userservice.findBookingsByUsername(user.getUsername()));
		model.addAttribute("event", event);
		model.addAttribute("newBooking", new Booking());
		return "edit";
	}

	@ModelAttribute("payment")
	public Payment getPayment(Authentication authentication) {
		User user = getUser(authentication);
		Payment payment = new Payment();
		payment.setNoOfItems(1);
		payment.setEmail(user.getUsername());
		payment.setUser(user);
		payment.setName(user.getFirstName() + " " + user.getLastName());
		payment.setPhone(user.getPhone());
		payment.setTransactionId("TRN" + LocalDateTime.now().getNano());
		return payment;
	}

	@ModelAttribute(CommonConstants.BOOKING_ATTRIBUTE)
	public Booking getBooking(Authentication authentication) {
		Booking booking = new Booking();
		booking.setBookingDesc("Free Booking");
		booking.setPayment(getPayment(authentication));
		return booking;
	}

	@ModelAttribute(CommonConstants.USER_ATTRIBUTE)
	public User getUser(Authentication authentication) {
		User user = new User();
		Object myUser = (authentication != null) ? authentication.getPrincipal() : null;
		if (myUser instanceof UserDetails) {
			user = (User) myUser;
		}
		return user;
	}

	@ModelAttribute("bookings")
	public List<Booking> getBookings(Authentication authentication) {
		User user = getUser(authentication);
		List<com.astrology.web.astroweb.domain.Booking> userBookings = (List<com.astrology.web.astroweb.domain.Booking>) userservice
				.findBookingsByUsername(user.getUsername());
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
		List<Booking> bookings = userBookings.stream().map(userBooking -> {
			Booking booking = new Booking();
			booking.setBookingDesc(userBooking.getBookingDesc());
			booking.setStartTime(userBooking.getStartTime().format(formatter));
			booking.setEndTime(userBooking.getEndTime().format(formatter));
			return booking;
		}).collect(Collectors.toList());
		return bookings;
	}

	@ModelAttribute("products")
	public List<Payment> getProducts(Authentication authentication) {
		final User user = getUser(authentication);
		List<com.astrology.web.astroweb.domain.Product> userProducts = (List<com.astrology.web.astroweb.domain.Product>) userservice
				.findByUsername(user.getUsername()).getProducts();
		List<Payment> payments = userProducts.stream().map(userProduct -> {
			Payment payment = new Payment();
			payment.setDescription(userProduct.getProductName());
			payment.setEmail(user.getUsername());
			payment.setName(userProduct.getProductName());
			payment.setNoOfItems(Integer.valueOf(userProduct.getNoOfProducts()));
			payment.setPhone(user.getPhone());
			payment.setUser(user);
			return payment;
		}).collect(Collectors.toList());
		return payments;
	}
}
