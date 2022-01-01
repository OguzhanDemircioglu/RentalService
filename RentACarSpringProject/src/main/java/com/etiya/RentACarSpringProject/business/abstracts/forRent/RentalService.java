package com.etiya.RentACarSpringProject.business.abstracts.forRent;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forRent.RentalDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.CreateRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.UpdateRentalRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.Rental;

public interface RentalService {
	
	DataResult<List<Rental>> findAll();
	
	DataResult<List<RentalDto>> getAll();

	DataResult<Rental> findById(int rentalId);
	
	DataResult<RentalDto> getById(int rentalId);
	
	Result existsByUserId(int applicationUserId);

	Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateRentalRequest updateRentalRequest, CreateCreditCardRequest createCreditCardRequest);

	Result delete(DeleteRentalRequest deleteRentalRequest);
	
	DataResult<CreateInvoiceRequest> createRentalInvoiceRequest(CreateRentalRequest createRentalRequest, Rental rental);
	Result checkPosService(CreateCreditCardRequest createCreditCardRequest, CreateRentalRequest createRentalRequest) ;

}
