package com.etiya.RentACarSpringProject.business.requests.authenticationRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRegisterRequest {


	@JsonIgnore
	private int applicationUserId;

	@JsonIgnore
	private int individualCustomerId;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@NotNull
	@Size(min=11, max=11)
	private String nationalIdentityNumber;;
	
	@Size(min=6, max=20)
	@NotNull
	@Email
	private String email;
	
	@Size(min=6, max=20)
	@NotNull
	private String password;
	
	@NotNull
	private String passwordConfirm;
}
