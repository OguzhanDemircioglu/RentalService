package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDto;
import com.etiya.RentACarSpringProject.business.requests.carRequest.CreateCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.DeleteCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.UpdateCarRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.Car;
import com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail;

public interface CarService {

	DataResult<List<Car>> findAll();

	DataResult<List<CarDto>> getAll();

	DataResult<Car> findById(int carId);  

	DataResult<CarDto> getById(int carId);

	DataResult<List<Car>> findCarsByColorId(int colorId);

	DataResult<List<CarDto>> getCarsByColorId(int colorId);
	
	DataResult<List<Car>> findCarsByBrandId(int brandId);

	DataResult<List<CarDto>> getCarsByBrandId(int brandId);

	DataResult<List<Car>> findByCity(int cityId);

	DataResult<List<CarDto>> getByCity(int cityId);

	DataResult<List<Car>> findAllAvailableCars();

	DataResult<List<CarDto>> getAllAvailableCars();

	DataResult<List<CarwithBrandandColorDetail>> getCarsWithDetails();

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);

	Result  checkIfCarIdExists(int carId);
}
