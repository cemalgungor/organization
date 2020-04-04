package com.cemal.organization.service;

import java.util.List;

import com.cemal.organization.model.Product;

public interface ProductService {

	List<Product> getAllProduct();

	Boolean deleteProduct(Long id);

	Product updateProduct(Product product);

	Product addProduct(Product product);

}
