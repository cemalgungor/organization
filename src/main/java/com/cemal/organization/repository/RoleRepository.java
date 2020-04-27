package com.cemal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cemal.organization.model.Role;



public interface RoleRepository extends JpaRepository <Role, Long> {

	Role findByName(String string);

}
