package com.cemal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cemal.organization.model.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}
