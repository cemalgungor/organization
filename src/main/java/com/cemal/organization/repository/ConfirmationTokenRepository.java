package com.cemal.organization.repository;

import org.springframework.data.repository.CrudRepository;

import com.cemal.organization.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
