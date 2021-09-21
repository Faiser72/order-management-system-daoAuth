package com.aspiresys.oms.model.service.order;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.aspiresys.oms.beans.Order;

public interface OrderService {

	public Order save(Order orderBean);

	public Order findById(long id);

	public List<Order> getOrderByDates(LocalDate startDate, LocalDate endDate);

	public List<Order> getAllOrders();

	public List<Order> getAllOrdersByCity(String shippingAddress);

	public List<Order> findByOrderDate(LocalDate date);

}
