package com.etiya.RentACarSpringProject.business.requests.brandRequest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	@JsonIgnore
	private int brandId;
	
	@NotNull
	private String brandName;
}
