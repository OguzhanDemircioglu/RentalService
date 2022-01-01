package com.etiya.RentACarSpringProject.ws.forUser;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.AuthenticationService;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateCorporateCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateIndividualCustomerRegisterRequest;
import com.etiya.RentACarSpringProject.business.requests.authenticationRequests.CreateLoginRequest;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/authentication")
public class AuthenticationController {
	private AuthenticationService authenticationService;

	@Autowired
	public AuthenticationController(AuthenticationService authenticationService) {
		super();
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/individualCustomerRegister")
	Result individualCustomerRegister( @RequestBody @Valid CreateIndividualCustomerRegisterRequest registerIndividualCustomerRequest) {
		return this.authenticationService.individualCustomerRegister(registerIndividualCustomerRequest);
	}
	
	@PostMapping("/corporateCustomerRegister")
	Result corporateCustomerRegister( @RequestBody @Valid CreateCorporateCustomerRegisterRequest registerCorporateCustomerRequest) {
		return this.authenticationService.corporateCustomerRegister(registerCorporateCustomerRequest);
	}
	
	@PostMapping("/login")
	Result login( @RequestBody @Valid CreateLoginRequest createLoginRequest) {
		return this.authenticationService.login(createLoginRequest);
	}
	
}
