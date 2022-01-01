package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.CarImageDto;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.UpdateCarImageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.CarImage;


public interface CarImageService {
	
	DataResult<List<CarImage>> findAll();

	DataResult<List<CarImageDto>> getAll();
	
	DataResult<CarImage> getById(int carImageId); 
	
	DataResult<List<CarImage>> findImagePathsByCarId(int carId);
	
	DataResult<List<CarImageDto>> getImagePathsByCarId(int carId);
	
	Result add(CreateCarImageRequest createCarImageRequest);

	Result update(UpdateCarImageRequest updateCarImageRequest);

	Result delete(DeleteCarImageRequest deleteCarImageRequest);
}
