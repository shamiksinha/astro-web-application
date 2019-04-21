package com.astrology.web.astroweb.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.astrology.web.astroweb.domain.User;
import com.astrology.web.astroweb.services.UserService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
 
    private UserService userService;
    private Converter<User, UserDetails> userUserDetailsConverter;
 
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
 
    @Autowired
    @Qualifier(value = "userToUserDetails")
    public void setUserUserDetailsConverter(Converter<User, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userUserDetailsConverter.convert(userService.findByUsername(username));
    }
}
