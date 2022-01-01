package com.etiya.RentACarSpringProject.business.dtos.forUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerDto {

	private int individualCustomerId;
	
	private String firstName;

	private String lastName;
	
	private String identityNumber;
	
	private ApplicationUserDto applicationUser;

	@JsonIgnore
	private  int userId;
}
