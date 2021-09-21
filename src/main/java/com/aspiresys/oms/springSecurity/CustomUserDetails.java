package com.aspiresys.oms.springSecurity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.aspiresys.oms.beans.User;

public class CustomUserDetails implements UserDetails {

	private User user;

//	when i will creste custom user details service, i'll pass user to this, the use of that is i can get role, pwd
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

//	the authorities which are available to a user
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

//		the role which is given is passed to simpleGrantedAuthority and it returns the role
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());

		List roles = Arrays.asList(simpleGrantedAuthority);
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
