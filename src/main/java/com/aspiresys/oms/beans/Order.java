package com.aspiresys.oms.beans;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "ordertab")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private long id;

	@Column(name = "ORDER_STATUS")
	private String orderStatus;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "product_order", joinColumns = @JoinColumn(name = "ORDER_ID", referencedColumnName = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID"))
	private List<Product> product;

	@Column(name = "ORDER_DATE")
	private LocalDate orderDate;

	@Column(name = "TOTAL_AMOUNT")
	private double totalAmount;

	@ManyToOne
	@JoinColumn(name = "USER")
	private User user;

	@Column(name = "SHIPPING_ADDRESS")
	private String shippingAddress;

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderStatus=" + orderStatus + ", orderDate=" + orderDate + ", totalAmount="
				+ totalAmount + ", user=" + user + ", shippingAddress=" + shippingAddress + "]";
	}

}
