package com.aspiresys.oms.model.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiresys.oms.beans.User;
import com.aspiresys.oms.model.repository.user.UserRepository;

import io.swagger.v3.oas.annotations.servers.Server;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;

	@Override
	public User save(User userBean) {
		return userRepository.save(userBean);
	}

}
