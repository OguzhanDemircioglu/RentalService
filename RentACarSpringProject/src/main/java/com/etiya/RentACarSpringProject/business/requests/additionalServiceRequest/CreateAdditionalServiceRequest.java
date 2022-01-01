package com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {

	@JsonIgnore
	private int additionalServiceId;
	
	@NotNull
	private String additionalServiceName;

	@Size(max = 100)
	@NotNull
	private String details;

	@NotNull
	private int price;

}
