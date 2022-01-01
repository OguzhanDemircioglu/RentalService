package com.etiya.RentACarSpringProject.business.requests.authenticationRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLoginRequest {

	@NotNull
	@Email
	private String email;
	
	@Size(min=6, max=20)
	@NotNull
	private String password;
}
