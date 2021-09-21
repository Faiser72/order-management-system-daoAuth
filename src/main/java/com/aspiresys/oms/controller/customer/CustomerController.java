package com.aspiresys.oms.controller.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.oms.beans.OmsResponse;
import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.model.repository.product.ProductRepository;
import com.aspiresys.oms.model.service.order.OrderService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserDetailsService userDetails;

//	This "addOrder" api is used to create the orders 

	@PostMapping(path = "/addOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse createOrder(@RequestBody Order orderBean, OmsResponse response) {
		if (orderBean.getProduct() != null) {
			orderBean.setOrderDate(LocalDate.now());
			orderBean.setOrderStatus("pending");
			orderBean.setTotalAmount(orderBean.getProduct().stream().mapToDouble(product -> product.getPrice()).sum());

			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				System.out.println(username);
			} else {
				String username = principal.toString();
				System.out.println(username);
			}
//			for (Product product: orderBean.getProduct()){
//				
//			}

			Order result = orderService.save(orderBean);
			if (result != null) {
				response.setObject(result);
				response.setMessage("Order Created Successfully");
				response.setStatus(200);
				response.setSuccess(true);
			} else {
				response.setMessage("Unable to Create the Order in to db, Please try again");
				response.setStatus(503);
				response.setSuccess(false);
			}
		} else {
			response.setMessage("Please Insert Data");
			response.setStatus(404);
			response.setSuccess(false);
		}
		return response;
	}

//	The api getOrderById is used to get the order details by orderId, we have to pass orderId
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getOrderById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getOrderById(@PathVariable long id, OmsResponse response) {
		if (id != 0) {
			Order order = orderService.findById(id);
			Order orderObj = new Order();
			System.err.println(order + "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");

			if (order != null) {
				orderObj.setId(order.getId());
				orderObj.setOrderDate(order.getOrderDate());
				orderObj.setOrderStatus(order.getOrderStatus());
				orderObj.setShippingAddress(order.getShippingAddress());
				orderObj.setTotalAmount(order.getTotalAmount());
				orderObj.setUser(order.getUser());

				response.setSuccess(true);
				response.setObject(orderObj);
				response.setStatus(200);
			} else {
				response.setSuccess(false);
				response.setObject("order id :" + id + " Not Exist in the db");
				response.setStatus(503);
			}
		} else {
			response.setSuccess(false);
			response.setObject("Please insert the id");
			response.setStatus(404);
		}
		return response;
	}

//	The api getOrderByDates is used to get the order details between two dates 
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getOrderByDates/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getOrderByDates(@PathVariable @DateTimeFormat(iso = ISO.DATE.DATE) LocalDate startDate,
			@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate endDate, OmsResponse response) {

		if (startDate != null && endDate != null) {
			List<Order> orders = orderService.getOrderByDates(startDate, endDate);
			List<Order> orderList = new ArrayList<>();
			if (!orders.isEmpty()) {
				for (Order order : orders) {
					Order ordeeObj = new Order();
					ordeeObj.setId(order.getId());
					ordeeObj.setOrderDate(order.getOrderDate());
					ordeeObj.setOrderStatus(order.getOrderStatus());
					ordeeObj.setShippingAddress(order.getShippingAddress());
					ordeeObj.setTotalAmount(order.getTotalAmount());
					ordeeObj.setUser(order.getUser());
					orderList.add(ordeeObj);
				}
				response.setSuccess(true);
				response.setListObject(orderList);
				response.setStatus(200);
			} else {
				response.setSuccess(false);
				response.setObject("There is no data for the dates : " + startDate + " And " + endDate);
				response.setStatus(503);
			}

		} else {
			response.setSuccess(false);
			response.setObject("Please insert the dates");
			response.setStatus(404);
		}
		return response;
	}

}
