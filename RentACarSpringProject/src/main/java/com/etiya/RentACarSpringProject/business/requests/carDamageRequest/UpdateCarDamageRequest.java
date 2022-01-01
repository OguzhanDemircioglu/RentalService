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
public class UpdateCarDamageRequest {
	
	@NotNull
	private int carId;
	
	@NotNull
	private int carDamageId;

	@NotNull
	private String damageDetail;

}
