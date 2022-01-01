package com.etiya.RentACarSpringProject.ws.forRent;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.InvoiceService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.InvoiceDto;
import com.etiya.RentACarSpringProject.core.results.DataResult;

@RestController
@RequestMapping("api/invoices")
public class InvoiceController {
	
	private InvoiceService invoiceService;

	@Autowired
	public InvoiceController(InvoiceService invoiceService) {
		super();
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getAll")
	public DataResult<List<InvoiceDto>> getAll() {
		return this.invoiceService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<InvoiceDto> getById(int invoiceId) {
		return this.invoiceService.getById(invoiceId);
	}	

	@GetMapping("/getInvoicesByUserId")
	public DataResult<List<InvoiceDto>> getInvoicesByUserId(int userId) {

		return this.invoiceService.getByRental_ApplicationUser_UserId(userId);
	}

	@GetMapping("/findInvoicesBetween")
	public DataResult<List<InvoiceDto>> findInvoicesBetween(String endDate, String startDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date end = dateFormat.parse(endDate);
		Date start = dateFormat.parse(startDate);

		return this.invoiceService.findInvoicesBetween(start, end);
	}

}
