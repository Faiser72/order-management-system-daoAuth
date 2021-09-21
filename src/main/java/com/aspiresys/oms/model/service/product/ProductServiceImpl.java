package com.aspiresys.oms.model.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspiresys.oms.beans.Product;
import com.aspiresys.oms.model.repository.product.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product save(Product productBean) {
		return productRepository.save(productBean);
	}

}
