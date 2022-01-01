package com.etiya.RentACarSpringProject.business.requests.applicationUserRequest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationUserRequest {

	@JsonIgnore
	private int userId;
	
	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;
}