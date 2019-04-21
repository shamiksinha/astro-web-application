package com.astrology.web.astroweb.services.impl;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -3354351123237549581L;
	private Collection<SimpleGrantedAuthority> authorities;
	private String username;
	private String password;
	private boolean enabled = true;
	private boolean accountNonExpired=true;
	private boolean accountNonLocked=true;
	private boolean credentialsNonExpired=true;

}
