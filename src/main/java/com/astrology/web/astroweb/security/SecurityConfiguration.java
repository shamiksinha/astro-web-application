/**
 * 
 */
package com.astrology.web.astroweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.astrology.web.astroweb.services.EncryptionService;
import com.astrology.web.astroweb.util.Env;
import org.springframework.http.HttpMethod;

/**
 * @author shamikchandra.s
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	private AuthenticationProvider authenticationProvider;
	
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	
	

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests().antMatchers("/", "/index","/webjars/**","/css/**","/img/**","/register","/js/**","/audio/**").permitAll().anyRequest().authenticated()
		.and()
		.authorizeRequests().antMatchers(HttpMethod.POST,"/register").permitAll().anyRequest().authenticated()
		//.and()
		//.authorizeRequests().antMatchers("/booking").hasAuthority("USER")
		.and()
		.formLogin().loginPage("/index").successHandler(authenticationSuccessHandler)
		.and()
        .logout().logoutSuccessUrl("/index?logout").permitAll();
        
        httpSecurity.headers().frameOptions().disable();
		httpSecurity.csrf().csrfTokenRepository(new HttpSessionCsrfTokenRepository());

		if (Env.TEST.name().equals(env.getProperty("app.env"))) {
			httpSecurity.authorizeRequests().antMatchers("/console/**").permitAll().anyRequest().authenticated();
			httpSecurity.csrf().disable();
			//httpSecurity.headers().frameOptions().disable();
		}
	}
	
	
	 
    @Autowired
    @Qualifier("daoAuthenticationProvider")
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }    
 
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService){
 
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
 
    @Autowired
    public void configureAuthManager(AuthenticationManagerBuilder authenticationManagerBuilder,UserDetailsService userDetailsService,AuthenticationProvider authenticationProvider) throws Exception{
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        authenticationManagerBuilder.eraseCredentials(false);
    }

    @Autowired
	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
		this.authenticationSuccessHandler=authenticationSuccessHandler;
	}
    
    /*@Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomUrlAuthenticationSuccessHandler();
    }*/
}
