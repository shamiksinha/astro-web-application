package com.astrology.web.astroweb;

//import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.astrology.web.astroweb.util.Env;

@Configuration
public class WebConfiguration {
	
	@Autowired
	private Environment env;
	
    //@Bean
    public ServletRegistrationBean h2servletRegistration(){
    	ServletRegistrationBean registrationBean=null;
    	/*if (Env.TEST.name().equals(env.getProperty("app.env"))) {
	        registrationBean = new ServletRegistrationBean( new WebServlet());
	        registrationBean.addUrlMappings("/console/*");
    	}*/
        return registrationBean;
   
    }
}
