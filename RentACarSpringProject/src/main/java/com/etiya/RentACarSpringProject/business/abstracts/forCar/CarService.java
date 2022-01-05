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

	DataResult<List<CarDto>> getAll();

	DataResult<Car> getById(int carId);

	DataResult<List<CarDto>> getCarsByColorId(int colorId);

	DataResult<List<CarDto>> getCarsByBrandId(int brandId);

	DataResult<List<CarDto>> getByCity(int cityId);

	DataResult<List<CarDto>> getAllAvailableCars();

	DataResult<List<CarwithBrandandColorDetail>> getCarsWithDetails();

	Result add(CreateCarRequest createCarRequest);

	Result update(UpdateCarRequest updateCarRequest);

	Result delete(DeleteCarRequest deleteCarRequest);

	Result  checkIfCarIdExists(int carId);
}
