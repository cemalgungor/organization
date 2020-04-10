package com.cemal.organization.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cemal.organization.model.Product;
import com.cemal.organization.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private final ProductServiceImpl productServiceImpl;

	public ProductController(ProductServiceImpl productServiceImpl) {

		this.productServiceImpl = productServiceImpl;
	}
 
	@GetMapping
	public ResponseEntity<List<Product>> getAllBooks() {
		List<Product> data = productServiceImpl.getAllProduct();
		return ResponseEntity.ok(data);
	}
     
	@PutMapping
	public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {

		return ResponseEntity.ok(productServiceImpl.updateProduct(product));

	}
	@PreAuthorize("hasRole('USER')")
	@PostMapping
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
		return ResponseEntity.ok(productServiceImpl.addProduct(product));

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteProduct(@PathVariable(value = "id", required = true) Long id) {
		return ResponseEntity.ok(productServiceImpl.deleteProduct(id));
	}
}
