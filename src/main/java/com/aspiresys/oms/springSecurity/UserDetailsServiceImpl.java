package com.aspiresys.oms.springSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.aspiresys.oms.beans.User;
import com.aspiresys.oms.model.repository.user.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

//	here we get the data from db, and pass the user to UserDetails
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		fetching user from database
		User user = userRepository.getUserByUserName(username);

//		checking wether user is null or not, if its null we throw exception
		if (user == null) {
			throw new UsernameNotFoundException("Could not found user !!");
		}

//		passing user to customUserdaetails
		CustomUserDetails customerUserDetails = new CustomUserDetails(user);

		return customerUserDetails;
	}

}
