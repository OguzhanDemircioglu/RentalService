package com.etiya.RentACarSpringProject.business.dtos.forCar;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

	private int carId;

	private String carName;

	private int modelYear;

	private double dailyPrice;

	private String description;
	
	private int minFindeksScore;
	
	private int cityId;

	private int brandId;

	private int colorId;

	private int km;
	
	private boolean inRepair;


	
	private List<CarImageDto> carImages;
	
	private List<CarDamageDto> carDamages;
	
}
