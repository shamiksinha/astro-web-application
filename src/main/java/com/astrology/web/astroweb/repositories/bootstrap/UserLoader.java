package com.astrology.web.astroweb.repositories.bootstrap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.services.UserService;
import com.astrology.web.astroweb.util.Env;

@Component
public class UserLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;

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
