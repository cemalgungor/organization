package com.cemal.organization.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cemal.organization.model.Product;
import com.cemal.organization.repository.ProductRepo;
import com.cemal.organization.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepo productRepo;

	public ProductServiceImpl(ProductRepo productRepo) {

		this.productRepo = productRepo;

	}

	@Override
	public List<Product> getAllProduct() {

		return productRepo.findAll();
	}

	@Override
	public Boolean deleteProduct(Long id) {
		productRepo.deleteById(id);
		if (productRepo.findById(id) != null) {
			return false;

		}
		return true;
	}

	@Override
	public Product updateProduct(Product product) {
		Product product1 = new Product();
		product1.setProduct_id(product.getProduct_id());
		product1.setProductCode(product.getProductCode());
		product1.setCreatedDate(product.getCreatedDate());
		product1.setProductName(product.getProductName());
		product1.setProductPiece(product.getProductPiece());
		product1.setProductPrice(product.getProductPrice());

		return productRepo.save(product1);
	}

	@Override
	public Product addProduct(Product product) {

		return productRepo.save(product);
	}

}
