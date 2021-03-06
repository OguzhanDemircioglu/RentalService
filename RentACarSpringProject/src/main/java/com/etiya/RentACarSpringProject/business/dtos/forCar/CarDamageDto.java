package com.etiya.RentACarSpringProject.business.dtos.forCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageDto {

	private int damageId;
	
	private String damageDetail;
	
	private int carId;
}
