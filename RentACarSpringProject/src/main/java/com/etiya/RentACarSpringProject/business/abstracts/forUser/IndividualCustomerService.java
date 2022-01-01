package com.etiya.RentACarSpringProject.business.abstracts.forUser;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forUser.IndividualCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.IndividualCustomer;

public interface IndividualCustomerService {
	
	DataResult<List<IndividualCustomer>> findAll();

	DataResult<List<IndividualCustomerDto>> getAll();

	DataResult<IndividualCustomer> findById(int individualCustomerId);
	
	DataResult<IndividualCustomerDto> getById(int individualCustomerId);

	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);

	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);

	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);
	
	Result existsByUserId(int applicationUserId);
	
	DataResult<IndividualCustomer> getByApplicationUser_UserId(int applicationUserId);
}
