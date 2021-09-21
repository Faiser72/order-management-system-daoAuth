package com.aspiresys.oms.model.service.order;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aspiresys.oms.beans.OmsResponse;
import com.aspiresys.oms.beans.Order;
import com.aspiresys.oms.model.repository.order.OrderRepository;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Order save(Order orderBean) {
		return orderRepository.save(orderBean);
	}

	@Override
	@Transactional
	public Order findById(long id) {
		Order order = null;
		OmsResponse response = null;
		try {
			order = orderRepository.findById(id).get();

			System.out.println(order);
			if (order == null) {
				System.out.println("hiiiiiiiiiiii");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.setMessage("no value found");
			response.setStatus(404);
		}

		return order;
	}

	@Override
	@Transactional
	public List<Order> getOrderByDates(LocalDate startDate, LocalDate endDate) {
		List<Order> Orders = null;
		try {
			Orders = orderRepository.getOrderByDates(startDate, endDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Orders;
	}

	@Override
	public List<Order> getAllOrders() {

		return (List<Order>) orderRepository.findAll();
	}

	@Override
	public List<Order> getAllOrdersByCity(String shippingAddress) {

		return orderRepository.getAllOrdersByCity(shippingAddress);
	}

	@Override
	public List<Order> findByOrderDate(LocalDate date) {
		System.out.println(orderRepository.findByOrderDate(date));
		return orderRepository.findByOrderDate(date);
	}

}
