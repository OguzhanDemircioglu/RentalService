package com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest;


import javax.validation.constraints.Size;

import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

	@JsonIgnore
	private int individualCustomerId;
	
	@NotNull
	private String firstName;

	@NotNull
	private String lastName;
	
	@NotNull
	@Size(min=11, max=11)
	private String nationalIdentityNumber;
	
	@NotNull
	private ApplicationUserDto applicationUser;
}