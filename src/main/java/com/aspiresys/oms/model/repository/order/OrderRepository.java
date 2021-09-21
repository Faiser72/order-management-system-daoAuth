package com.aspiresys.oms.model.repository.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aspiresys.oms.beans.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query(value = "select * from ordertab where order_date BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<Order> getOrderByDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

	@Query("From Order WHERE shippingAddress = :shippingAddress")
	List<Order> getAllOrdersByCity(@Param("shippingAddress") String shippingAddress);

	List<Order> findByOrderDate(LocalDate date);
}
