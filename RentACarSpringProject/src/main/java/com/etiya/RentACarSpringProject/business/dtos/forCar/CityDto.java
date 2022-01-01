package com.etiya.RentACarSpringProject.business.dtos.forCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private int cityId;

    private String cityName;
}
