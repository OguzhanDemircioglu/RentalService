package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDamageDto;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.CreateCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.DeleteCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.UpdateCarDamageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.CarDamage;

public interface CarDamageService {
	
	DataResult<List<CarDamage>> findAll();
	
	DataResult<List<CarDamageDto>> getAll();
	
	DataResult<List<CarDamage>> findByCarId(int carId);
	
	DataResult<List<CarDamageDto>> getByCarId(int carId);

	Result add(CreateCarDamageRequest createCarDamageRequest);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

	Result delete(DeleteCarDamageRequest deleteCarDamageRequest);
}
