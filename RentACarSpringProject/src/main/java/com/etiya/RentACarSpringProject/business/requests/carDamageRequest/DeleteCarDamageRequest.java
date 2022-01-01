package com.etiya.RentACarSpringProject.business.requests.carDamageRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarDamageRequest {
	
	@NotNull
	private int carDamageId;
	
}
