package com.etiya.RentACarSpringProject.business.requests.carRequest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
	
	@NotNull
	private int carId;
	
	@NotNull
	private String carName;
	
	@NotNull
	private int brandId;
	
	@NotNull
	private int colorId;

	@NotNull
	@Min(2000)
	@Max(2025)
	private int modelYear;

	@NotNull
	@Min(100)
	private double dailyPrice;

	@NotNull
	@Size(max = 100)
	private String description;
	
	@NotNull
	private int minFindeksScore;
	
	@NotNull
	private int cityId;
	
	@NotNull
	private int km;

	@JsonIgnore
	private boolean inRepair;
	
}
