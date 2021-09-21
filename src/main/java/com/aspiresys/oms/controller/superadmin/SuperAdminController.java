package com.aspiresys.oms.controller.superadmin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.oms.beans.OmsResponse;
import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.model.service.order.OrderService;

@RestController
@RequestMapping("super-admin")
public class SuperAdminController {

	@Autowired
	private OrderService orderService;

//	The api getOrderByDates is used to get the order details between two dates 
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getOrderByDatesAsc/{startDate}/{endDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getOrderByDates(@PathVariable @DateTimeFormat(iso = ISO.DATE.DATE) LocalDate startDate,
			@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate endDate, OmsResponse response) {

		if (startDate != null && endDate != null) {
			List<Order> orders = orderService.getOrderByDates(startDate, endDate);
			List<Order> ascOrderList = orders.stream()
					.sorted((obj1, obj2) -> obj1.getOrderDate().compareTo(obj2.getOrderDate()))
					.collect(Collectors.toList()); //
			List<Order> orderList = new ArrayList<>();
			if (!orders.isEmpty()) {
				for (Order order : ascOrderList) {
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

//	Api to get the orders Details by shipping address
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getAllOrderByCity/{shippingAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getAllOrderByCity(@PathVariable String shippingAddress, OmsResponse response) {

		if (!shippingAddress.equals(null)) {

//			List<Order> orders = orderService.getAllOrdersByCity(shippingAddress);
			List<Order> orders = orderService.getAllOrders();
			List<Order> filteredOrder = orders.stream()
					.filter(order -> order.getShippingAddress().equals(shippingAddress)).collect(Collectors.toList());
			List<Order> orderList = new ArrayList<>();
			if (!filteredOrder.isEmpty()) {
				response.setListObject(filteredOrder);
				for (Order order : filteredOrder) {
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
				response.setObject("There is no data");
				response.setStatus(503);
			}

		} else {
			response.setSuccess(false);
			response.setObject("Please insert the City");
			response.setStatus(404);
		}
		return response;
	}

//	Api to get the sum of all the objects which got from input date
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getSumOfOrderByDate/{orderDate}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getSumOfOrderByDate(@PathVariable @DateTimeFormat(iso = ISO.DATE.DATE) LocalDate orderDate,
			OmsResponse response) {

		if (orderDate != null) {
			double sumOfOrders;
			List<Order> orders = orderService.findByOrderDate(orderDate);

			sumOfOrders = orders.stream().mapToDouble(order -> order.getTotalAmount()).sum();

			List<Order> orderList = new ArrayList<>();

			Map<String, Double> map = new HashMap<>();
			map.put("Sum", sumOfOrders);

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
				response.setMap(map);
			} else {
				response.setSuccess(false);
				response.setObject("There is no data");
				response.setStatus(503);
			}

		} else {
			response.setSuccess(false);
			response.setObject("Please insert the Date");
			response.setStatus(404);
		}
		return response;
	}

//	Api to get orderdetails  by orderDate and orderStatus
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getOrderByDateAndStatus/{orderDate}/{orderStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getOrderByDateAndStatus(@PathVariable @DateTimeFormat(iso = ISO.DATE.DATE) LocalDate orderDate,
			@PathVariable String orderStatus, OmsResponse response) {

		if (orderDate != null && !orderStatus.equals(null)) {
			List<Order> orders = orderService.getAllOrders();
			List<Order> filteredOrder = orders.stream().filter(
					order -> order.getOrderDate().equals(orderDate) && order.getOrderStatus().equals(orderStatus))
					.collect(Collectors.toList());

			List<Order> orderList = new ArrayList<>();

			if (!filteredOrder.isEmpty()) {
				for (Order order : filteredOrder) {
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
				response.setObject("There is no data");
				response.setStatus(503);
			}

		} else {
			response.setSuccess(false);
			response.setObject("Please insert the proper Date status");
			response.setStatus(404);
		}
		return response;
	}

// Api to get the orderStatus cound based on date and status
	@SuppressWarnings("unchecked")
	@GetMapping(path = "/getStatusCountByDateAndStatus/{orderDate}/{orderStatus}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getStatusCountByDateAndStatus(
			@PathVariable @DateTimeFormat(iso = ISO.DATE.DATE) LocalDate orderDate, @PathVariable String orderStatus,
			OmsResponse response) {
		System.out.println(orderDate + "dddddddddddddddddddd");

		if (orderDate != null && !orderStatus.equals(null)) {
			List<Order> orders = orderService.getAllOrders();
			List<Order> filteredOrder = orders.stream().filter(
					order -> order.getOrderDate().equals(orderDate) && order.getOrderStatus().equals(orderStatus))
					.collect(Collectors.toList());

			long pendingCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("pending"))
					.count(); // to get the count of status pending
			long approvedCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("approved"))
					.count(); // to get the count of status approved
			long denyCount = filteredOrder.stream().filter(order -> order.getOrderStatus().equals("deny")).count(); // to
																													// get
																													// the
																													// count
																													// of
																													// status
																													// deny

			List<Order> orderList = new ArrayList<>();

			Map<String, Long> statusCount = new HashMap<>();
			statusCount.put("Pending", pendingCount);
			statusCount.put("Approved", approvedCount);
			statusCount.put("Deny", denyCount);
			System.out.println(filteredOrder + "filgterrrrr");

			if (!filteredOrder.isEmpty()) {
				for (Order order : filteredOrder) {
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
				response.setMap(statusCount);
			} else {
				response.setSuccess(false);
				response.setMessage("There is no data");
				response.setStatus(503);
			}

		} else {
			response.setSuccess(false);
			response.setMessage("Please insert the proper Date status");
			response.setStatus(404);
		}
		return response;
	}
}
