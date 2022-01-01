package com.etiya.RentACarSpringProject.business.requests.authenticationRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRegisterRequest {
	
	@NotNull
	private String companyName;
	
	@Size(min=10, max=10)
	@NotNull
	private String taxNumber;
	
	@NotNull
	@Email
	private String email;
	
	@Size(min=6, max=20)
	@NotNull
	private String password;
	
	@Size(min=6, max=20)
	@NotNull
	private String passwordConfirm;
}
