package com.astrology.web.astroweb.instamojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import com.astrology.web.astroweb.util.Env;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.InstamojoImpl;

@Configuration
public class Instamojo {
	
	private Environment env;
	
	@Autowired
	public void setEnv(Environment env) {
		this.env = env;
	}
	
	private ApiContext getTestContext() {
		return ApiContext.create(env.getRequiredProperty("instamojo.api.client.id"), env.getRequiredProperty("instamojo.api.client.secret"), ApiContext.Mode.TEST);
	}
	
	private ApiContext getProdContext() {
		return ApiContext.create(env.getRequiredProperty("instamojo.api.client.id"), env.getRequiredProperty("instamojo.api.client.secret"), ApiContext.Mode.LIVE);
	}
	
	@Bean
	@Scope(scopeName="prototype")
	public com.instamojo.wrapper.api.Instamojo getInstamojo() {
		return new InstamojoImpl(getContext());
	}

	private ApiContext getContext() {
		return (Env.TEST.name().equals(env.getProperty("app.env")))?getTestContext():getProdContext();
	}
}
