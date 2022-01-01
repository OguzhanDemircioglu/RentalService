package com.etiya.RentACarSpringProject.business.abstracts.forUser;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forUser.CorporateCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.DeleteCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.UpdateCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.CorporateCustomer;

public interface CorporateCustomerService {
	
	DataResult<List<CorporateCustomer>> findAll();
	
	DataResult<List<CorporateCustomerDto>> getAll();

	DataResult<CorporateCustomer> findById(int corporateCustomerId);

	DataResult<CorporateCustomerDto> getById(int corporateCustomerId);

	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);

	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);

	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);

	Result existsByUserId(int applicationUserId);
	
	DataResult<CorporateCustomer> getByApplicationUser_UserId(int applicationUserId);
	
}
