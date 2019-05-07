package com.astrology.web.astroweb.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Transient;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6578721060265951297L;

	@ToString.Exclude
	private String password;

	@ToString.Exclude
	@Transient
	private String confirmPassword;
	private String username;
	private final Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();
	private final boolean accountNonExpired=true;
	private final boolean accountNonLocked=true;
	private final boolean credentialsNonExpired=true;
	private final boolean enabled=true;
	private String firstName;
    private String lastName;
    private String phone;

}
