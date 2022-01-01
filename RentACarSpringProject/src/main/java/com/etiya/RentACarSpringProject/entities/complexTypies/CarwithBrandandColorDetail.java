package com.etiya.RentACarSpringProject.entities.complexTypies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarwithBrandandColorDetail {
	
	private int id;
	
	private String carName;
	
	private String brandName;
	
	private String colorName;
	
	private double dailyPrice;
	
}
