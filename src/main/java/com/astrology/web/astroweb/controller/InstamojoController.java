package com.astrology.web.astroweb.controller;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.astrology.web.astroweb.model.Booking;
import com.astrology.web.astroweb.model.Payment;
import com.astrology.web.astroweb.model.User;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InstamojoController {

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

	@GetMapping("/paymentComplete")
	public String createPaymentRequest(Model model, Authentication authentication,
			@RequestParam("payment_id") String paymentId, @RequestParam("payment_status") String status,
			@RequestParam("transaction_id") String transactionId, @RequestParam("id") String id,RedirectAttributes rdAttributes) {
		String url="redirect:/userconfirmation";
		logger.info("Inside createPaymentRequest with Transaction Id "+transactionId);
		boolean isSuccess=false;
		if (paymentId != null && id != null && transactionId != null && status != null) {
			Payment payment = new Payment();
			payment.setPaymentId(paymentId);
			payment.setPaymentStatus(status.toUpperCase());
			payment.setUniqueId(id);
			payment.setTransactionId(transactionId);
			payment.setUser(getUser(authentication));
			com.astrology.web.astroweb.domain.User user = userservice.updatePaymentOrder(payment);
			if (user == null) {
				logger.error("Error while saving data");
				model.addAttribute("msg", "Error while saving data");
				
			}else{
				model.addAttribute("msg", "Successfully completed the payment");
			}
			model.addAttribute("paymentStatus", status);
			isSuccess=true;						
		}
		rdAttributes.addAttribute("msg",model.asMap().get("msg"));
		rdAttributes.addAttribute("paymentStatus",model.asMap().get("paymentStatus"));
		rdAttributes.addAttribute("status", isSuccess);
		return url;
	}

	@GetMapping("/userconfirmation")
	public String getUserConfirmation(Model model,@RequestParam("msg") String msg,@RequestParam("status") boolean isSuccess,@RequestParam("paymentStatus") String paymentStatus){
		logger.info("Inside getUserConfirmation");
		if (isSuccess){
			model.addAttribute("success", msg);
		}else{
			model.addAttribute("error", msg);
		}
		model.addAttribute("paymentStatus", paymentStatus=="null"?null:paymentStatus);
		return "userconfirmation";
	}

	@PostMapping("/buy")
	public String payment(Payment payment, Authentication authentication, HttpServletRequest request) throws ConnectionException, HTTPException {
		logger.info("Payment data from view " + payment);
		String url = "home";
		/*
		 * PaymentRequest paymentRequest=new PaymentRequest();
		 * paymentRequest.setAmount(100.00);
		 * paymentRequest.setPurpose("Purpose");//noOfItems+" "+ product+"s");
		 * paymentRequest.setEmail("abc@cde.com"); String url =
		 * instamojo.createPaymentRequest(paymentRequest).getLongUrl();
		 * logger.info("Payment URL "+url);
		 */
		/*
		 * Create a new payment order
		 */

		payment.setAmount(payment.getNoOfItems().doubleValue() * 9D);
		String redirectUrlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/";
		com.astrology.web.astroweb.domain.User user = userservice.savePaymentOrder(payment);
		if (user != null) {
			PaymentOrder order = new PaymentOrder();
			order.setName(payment.getName());
			order.setEmail(payment.getEmail());
			order.setPhone(payment.getPhone());
			order.setCurrency(payment.getCurrencyCode());
			order.setAmount(payment.getAmount());
			order.setDescription(payment.getDescription());
			order.setRedirectUrl(redirectUrlPrefix+"paymentComplete");
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

			} catch (ConnectionException e) {
				logger.error(e.getMessage());
			}
		}
		return "redirect:" + url;// "redirect:"+url;
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
	public Booking getBooking() {
		Booking booking = new Booking();
		booking.setBookingDesc("Free Booking");
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

}
