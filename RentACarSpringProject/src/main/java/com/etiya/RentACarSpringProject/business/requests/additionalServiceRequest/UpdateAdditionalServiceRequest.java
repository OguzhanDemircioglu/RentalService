package com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	@NotNull
	private int additionalServiceId;
	
	@NotNull
	private String additionalServiceName;

	@Size(max = 100)
	@NotNull
	private String details;

	@Min(10)
	@NotNull
	private int price;
}
