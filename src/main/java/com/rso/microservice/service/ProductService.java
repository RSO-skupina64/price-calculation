package com.rso.microservice.service;

import com.rso.microservice.entity.Product;
import com.rso.microservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	Optional<Product> findByIdWithProductShops(Long id) {
		return productRepository.findByIdWithProductShops(id);
	}
}
