package com.aspiresys.oms.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.oms.beans.OmsResponse;
import com.aspiresys.oms.beans.User;
import com.aspiresys.oms.model.service.user.UserService;

@RestController
//@RequestMapping("admin")
public class UserController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

//	Add User 
	@PostMapping(path = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse addUser(@RequestBody User userBean, OmsResponse response) {
		userBean.setActiveFlag(1);
		userBean.setPassword(passwordEncoder.encode(userBean.getPassword()));
		User result = userService.save(userBean);
		if (result != null) {
			response.setObject(userBean);
			response.setStatus(200);
			response.setMessage("Saved Successfully");
			response.setSuccess(true);
		} else {
			response.setSuccess(false);
			response.setStatus(403);
			response.setMessage("Please Try again");
		}
		return response;
	}
}
