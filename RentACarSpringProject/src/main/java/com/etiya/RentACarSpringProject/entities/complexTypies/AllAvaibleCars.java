package com.etiya.RentACarSpringProject.entities.complexTypies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllAvaibleCars {
	private int carId;
    private String carName;
    private int modelYear;
    private double dailyPrice;
    private String description;
    private int cityId;
    private boolean inRepair;
    private int brandId;
    private int colorId;
    private boolean returned;
}
