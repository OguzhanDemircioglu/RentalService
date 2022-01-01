package com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest;


import javax.validation.constraints.Size;


import com.etiya.RentACarSpringProject.business.dtos.forUser.ApplicationUserDto;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	@NotNull
	private int corporateCustomerId;

	@NotNull
	private String companyName;

	@NotNull
	@Size(min=10, max=10)
	private String taxNumber;

	@NotNull
	private ApplicationUserDto applicationUser;
}