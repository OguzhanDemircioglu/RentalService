package com.etiya.RentACarSpringProject.business.requests.applicationUserRequest;

import javax.validation.constraints.Email;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationUserRequest {
	
	@NotNull
	private int userId;

	@NotNull
	@Email
	private String email;

	@NotNull
	private String password;
	
	@NotNull
	private String passwordConfirm;
}