package com.etiya.RentACarSpringProject.ws.forCar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarDamageService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDamageDto;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.CreateCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.DeleteCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.UpdateCarDamageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;



@RestController
@RequestMapping("api/carDamages")
public class CarDamageController {
	
	CarDamageService carDamageService;

	@Autowired
	public CarDamageController(CarDamageService carDamageService) {
		super();
		this.carDamageService = carDamageService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarDamageDto>> getAll() {
		return this.carDamageService.getAll();
	}
	
	@GetMapping("/getByCarId")
	public DataResult<List<CarDamageDto>> getByCarId(int carId) {
		return this.carDamageService.getByCarId(carId);
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) {
		return this.carDamageService.add(createCarDamageRequest);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
		return this.carDamageService.update(updateCarDamageRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delte(DeleteCarDamageRequest deleteCarDamageRequest) {
		return this.carDamageService.delete(deleteCarDamageRequest);
	}
}
