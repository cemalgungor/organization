package com.cemal.organization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cemal.organization.model.Invoice;
@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, Long> {

}
