package com.astrology.web.astroweb.security;

import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.astrology.web.astroweb.domain.User;
import org.springframework.util.Assert;

@Component
public class UserToUserDetails implements Converter<User, UserDetails> {
    @Override
    public UserDetails convert(User user) {
        com.astrology.web.astroweb.model.User userDetails = new com.astrology.web.astroweb.model.User();
 
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
            //userDetails.setEnabled(true);
            Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
           /* user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            });*/
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            //userDetails.getAuthorities().add(new SimpleGrantedAuthority(user.getRole()));
            userDetails.setFirstName(user.getFirstName());
            userDetails.setLastName(user.getLastName());
            userDetails.setPhone(user.getPhone());
        }
 
        return userDetails;
    }
}
