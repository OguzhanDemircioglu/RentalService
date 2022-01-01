package com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIndividualCustomerRequest {

	@NotNull
	private int individualCustomerId;
}