package com.cemal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cemal.organization.model.User;


public interface UserRepo extends JpaRepository<User, Long>{
	User findByUsername(String username);
}
