package com.astrology.web.astroweb.repositories.bootstrap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.astrology.web.astroweb.domain.BookingType;
import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.services.BookingTypeService;
import com.astrology.web.astroweb.services.UserService;
import com.astrology.web.astroweb.util.Env;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	
	private Environment env;
	
	/**
	 * @param env the env to set
	 */
	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}
	
	private UserService userService;

	/**
	 * @param userService the userService to set
	 */
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private BookingTypeService bookingTypeService;

	/**
	 * @param bookingTypeService the bookingTypeService to set
	 */
	@Autowired
	public void setBookingTypeService(BookingTypeService bookingTypeService) {
		this.bookingTypeService = bookingTypeService;
	}
    private final Logger log = LogManager.getLogger(this.getClass());
    
    /*@Autowired
    private ApplicationContext context;*/

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Context refreshed. Reached UserLoader");		
		if (Env.TEST.name().equals(env.getProperty("app.env"))) {
			if (userService.count()!=0) {
				userService.deleteAll();
			}
			User user1=new User();
			user1.setFirstName("Shamik");
			user1.setLastName("Sinha");
			user1.setEmail("abc@cd.com");
			user1.setTextPass("shamik");
			user1.setUsername("abc@cd.com");
			user1.setPhone("+919916099778");
			//userService.saveOrUpdate(user1);
			User user2=new User();
			user2.setFirstName("Shamik1");
			user2.setLastName("Sinha1");
			user2.setEmail("abc@cde.com");
			user2.setTextPass("shamik");
			user2.setUsername("abc@cde.com");
			user2.setPhone("+919916099778");
			//userService.saveOrUpdate(user2);
			Set<User> users=new HashSet<User>();
			users.add(user1);
			users.add(user2);
			userService.saveAll(users);			
		}
		
		showSavedData(userService);
		/*String[] beanNames = context.getBeanDefinitionNames();
		log.info(Arrays.asList(beanNames));*/

		if (bookingTypeService.listAll().isEmpty()){
			BookingType bookingTypeFree=new BookingType();
			bookingTypeFree.setBookingTypeDesc("free");
			bookingTypeFree.setBookingTypeId("BT_0001");
			bookingTypeFree.setBookingTypeduration(15);
			bookingTypeService.saveOrUpdate(bookingTypeFree);

			BookingType bookingTypePaid=new BookingType();
			bookingTypePaid.setBookingTypeDesc("paid");
			bookingTypePaid.setBookingTypeId("BT_0002");
			bookingTypePaid.setBookingTypeduration(60);
			bookingTypeService.saveOrUpdate(bookingTypePaid);
		}

		showSavedData(bookingTypeService);
	}
	
	private void showSavedData(BookingTypeService bookingTypeService) {
		log.info(((List<BookingType>)bookingTypeService.listAll()).stream().map(BookingType::toString).collect(Collectors.joining(", ")));
	}

	private String getUserName(User user) {
		return user.getFirstName()+" "+user.getLastName();
	}

	private void showSavedData(UserService userService) {
		List<User> users =  (List<User>) userService.listAll();
		for (Iterator<User> itr = users.iterator();itr.hasNext();) {
			User data=itr.next();			
			log.info("Saved data "+ data.toString());
		}
	}



}
