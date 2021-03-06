package com.etiya.RentACarSpringProject.business.abstracts.forUser;

import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateLoginRequest;
import com.etiya.RentACarSpringProject.core.results.Result;


public interface AuthenticationService {
	
	Result individualCustomerRegister(CreateIndividualCustomerRegisterRequest registerIndividualCustomerRequest);

	Result corporateCustomerRegister(CreateCorporateCustomerRegisterRequest registerCorporateCustomerRequest);

	Result login(CreateLoginRequest createLoginRequest);
}
