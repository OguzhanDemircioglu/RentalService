package com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCorporateCustomerRequest {

	@NotNull
	private int corporateCustomerId;
}
