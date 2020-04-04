package com.cemal.organization.service;

import java.util.List;

import com.cemal.organization.model.Invoice;
public interface InvoiceService {
	List<Invoice> getAllInvoice();
	Invoice updateInvoice(Invoice ınvoice);
	Invoice addInvoice(Invoice ınvoice);
	Boolean deleteInvoice(Long id);
	

}
