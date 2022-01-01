package com.etiya.RentACarSpringProject.business.requests.brandRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBrandRequest {
	
	@NotNull
	private int brandId;
}