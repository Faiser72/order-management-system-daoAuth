package com.aspiresys.oms.controller.systemuser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspiresys.oms.beans.OmsResponse;
import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.beans.Product;
import com.aspiresys.oms.model.service.order.OrderService;
import com.aspiresys.oms.model.service.product.ProductService;

@RestController
@RequestMapping("system-user")
public class SystemUserController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

//	Api to add the product
	@PostMapping(path = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse addProduct(@RequestBody Product productBean, OmsResponse response) {
		System.out.println(productBean.getName() + productBean.getPrice());
		if (productBean.getName() != null) {
			productBean.setActiveFlag(1);
			productBean.setCreatedDate(LocalDate.now());
			Product product = productService.save(productBean);
			if (product != null) {
				response.setObject(product);
				response.setMessage("Product Saved Successfully");
				response.setStatus(200);
				response.setSuccess(true);
			} else {
				response.setMessage("Unable to save the product in to db, Please try again");
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
	@GetMapping(path = "/getOrderByIds/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse getOrderById(@PathVariable long id, OmsResponse response) {
		if (Long.valueOf(id) != null) {
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

//	The Api "aprooveStatus" api is used to approve the order which is pending/ deny
	@PutMapping(path = "/approveOrderStatus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse approveOrderStatus(@PathVariable long id, OmsResponse response) {
		if (Long.valueOf(id) != null) {
			Order orderDetails = orderService.findById(id);
			System.out.println(orderDetails.getOrderStatus() + "someeee");

			if (orderDetails.getOrderStatus().equals("pending") || orderDetails.getOrderStatus().equals("deny")) {
				orderDetails.setOrderStatus("approved");
				Order savedObj = orderService.save(orderDetails);
				if (savedObj != null) {
					response.setMessage("Order with Order Id : " + id + " Approved Successfully");
					response.setStatus(200);
					response.setSuccess(true);
				} else {
					response.setMessage("Unable to approve the order in to db, Please try again");
					response.setStatus(503);
					response.setSuccess(false);
				}
			} else {
				System.out.println("already approved order");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Please insert the id");
			response.setStatus(404);
		}
		return response;
	}

//	The Api "denayStatus" api is used to deny the order which is pending/approve
	@PutMapping(path = "/denyOrderStatus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse denyOrderStatus(@PathVariable long id, OmsResponse response) {
		if (Long.valueOf(id) != null) {
			Order orderDetails = orderService.findById(id);
			if (orderDetails.getOrderStatus().equals("deny") || orderDetails.getOrderStatus().equals("approved")) {
				orderDetails.setOrderStatus("deny");
				Order savedObj = orderService.save(orderDetails);
				if (savedObj != null) {
					response.setMessage("Order with Order Id : " + id + " is updated Successfully");
					response.setStatus(200);
					response.setSuccess(true);
				} else {
					response.setMessage("Unable to update the states  in to db, Please try again");
					response.setStatus(503);
					response.setSuccess(false);
				}
			} else {
				System.out.println("denyed order");
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Please insert the id");
			response.setStatus(404);
		}
		return response;
	}

//	Api to update user details
	@PutMapping(path = "/updateOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OmsResponse updateOrder(@RequestBody Order order, OmsResponse response) {
		if (order != null) {
//			Order orderDetails = orderService.findById(order.getId());
//
//			orderDetails.setId(order.getId());
//			orderDetails.setOrderDate(order.getOrderDate());
//			orderDetails.setOrderStatus(order.getOrderStatus());
//			orderDetails.setProduct(order.getProduct());
//			orderDetails.setShippingAddress(order.getShippingAddress());
//			orderDetails.setTotalAmount(order.getTotalAmount());
//			orderDetails.setUser(order.getUser());

			Order savedObj = orderService.save(order);

			if (savedObj != null) {
				response.setMessage("Order with Order Id : " + order.getId() + " Updated Successfully");
				response.setStatus(200);
				response.setSuccess(true);
			} else {
				response.setMessage("Unable to Update the order in to db, Please try again");
				response.setStatus(503);
				response.setSuccess(false);
			}
		} else {
			response.setSuccess(false);
			response.setMessage("Please insert the id");
			response.setStatus(404);
		}
		return response;
	}

}
