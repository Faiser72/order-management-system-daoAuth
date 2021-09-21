package com.aspiresys.oms.model.repository.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aspiresys.oms.beans.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
