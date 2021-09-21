package com.aspiresys.oms.beans;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID")
	private long id;

	@Column(name = "ACTIVE_FLAG")
	private int activeFlag;

	@Column(name = "PRODUCT_NAME")
	private String name;

	@Column(name = "PRODUCT_PRICE")
	private double price;

	@Column(name = "PRODUCT_QUANTITY")
	private long quantity;

	@Column(name = "CREATED_DATE")
	private LocalDate createdDate;

	@ManyToMany(mappedBy = "product")
	private List<Order> order;

	@Override
	public String toString() {
		return "Product [id=" + id + ", activeFlag=" + activeFlag + ", name=" + name + ", price=" + price
				+ ", quantity=" + quantity + ", createdDate=" + createdDate + "]";
	}

}
