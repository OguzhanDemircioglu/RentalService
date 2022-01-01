package com.etiya.RentACarSpringProject.business.dtos.forUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerDto {

	private int corporateCustomerId;
	
	private String companyName;
	
	private String taxNumber;
	
	private ApplicationUserDto applicationUser;
}
