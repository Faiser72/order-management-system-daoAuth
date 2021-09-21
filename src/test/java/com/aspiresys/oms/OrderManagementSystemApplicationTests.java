package com.aspiresys.oms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.beans.User;
import com.aspiresys.oms.model.repository.order.OrderRepository;
import com.aspiresys.oms.model.repository.user.UserRepository;

@SpringBootTest
class OrderManagementSystemApplicationTests {
	
	@Autowired
	private UserRepository UserRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; //to encode the password

	@Test
	void contextLoads() {
	}
	
//	Method to test add users
	@Test
	public void testAddUser() {
		User user = new User();
		user.setEmail("test@gmail.com");
		user.setGender("Male");
		user.setMobileNo("7878678767");
		user.setName("test");
		user.setPassword(passwordEncoder.encode("123456"));
		user.setRole("SYSTEM_ADMIN");
		UserRepository.save(user);
		assertNotNull(user);
	}
	
	@Test
	public void testGetById() {
		long l= 2l;
		Order order = orderRepository.findById(l).get();
		assertNotNull(order);
		assertEquals("approved",order.getOrderStatus());
		
	}
	
//	Method to test the findByOrderDate
	@Test
	public void testFindByOrderDate() {
		LocalDate date =  LocalDate.now();
		List<Order> orders = orderRepository.findByOrderDate(date);
		System.out.println(orders);
	}

}
