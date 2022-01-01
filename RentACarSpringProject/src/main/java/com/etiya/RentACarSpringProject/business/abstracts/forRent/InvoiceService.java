package com.etiya.RentACarSpringProject.business.abstracts.forRent;

import java.util.Date;
import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forRent.InvoiceDto;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.Invoice;

public interface InvoiceService {

	DataResult<List<Invoice>> findAll();
	
	DataResult<List<InvoiceDto>> getAll();
	
	DataResult<Invoice> findById(int invoiceId);

	DataResult<InvoiceDto> getById(int invoiceId);
	
	Result insert(CreateInvoiceRequest createInvoiceRequest, List<Integer> additionalServices);

	//Result update(UpdateInvoiceRequest updateInvoiceRequest);

	Result delete(DeleteInvoiceRequest deleteInvoiceRequest);
	
	DataResult<List<InvoiceDto>> findInvoicesBetween(Date endDate, Date startDate);

	DataResult<List<InvoiceDto>> getByRental_ApplicationUser_UserId(int userId);
}
