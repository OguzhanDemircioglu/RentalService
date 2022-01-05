package com.etiya.RentACarSpringProject.ws.forCar;

import java.util.List;

import javax.validation.Valid;

import com.etiya.RentACarSpringProject.entities.Car;
import com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDto;
import com.etiya.RentACarSpringProject.business.requests.carRequest.CreateCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.DeleteCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.UpdateCarRequest;
import com.etiya.RentACarSpringProject.core.results.*;

@RestController
@RequestMapping("api/cars")
public class CarController {
	CarService carService;

	@Autowired
	public CarController(CarService carService) {
		super();
		this.carService = carService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CarDto>> getAll() {
		return this.carService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<Car> getById(int carId) {
		return this.carService.getById(carId);
	}
	
	@GetMapping("/getCarsByColorId")
	public DataResult<List<CarDto>> getCarsByColorId(int colorId) {
		return this.carService.getCarsByColorId(colorId);
	}
	
	@GetMapping("/getCarsByBrandId")
	public DataResult<List<CarDto>> getCarsByBrandId(int brandId) {
		return this.carService.getCarsByBrandId(brandId);
	}
	
	@GetMapping("/getCarsByCity")
	public DataResult<List<CarDto>> getByCity(@RequestParam("cityId") int cityId){
		return this.carService.getByCity(cityId);
	}
	
	@GetMapping("/getAllAvailableCars")
	public DataResult<List<CarDto>> getAllAvailableCars(){
		return this.carService.getAllAvailableCars(); 
	}
	
	@GetMapping("/getCarsWithDetails")
	public DataResult<List<CarwithBrandandColorDetail>> getCarsWithDetails() {
		return this.carService.getCarsWithDetails();
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteCarRequest deleteCarRequest) {
		return this.carService.delete(deleteCarRequest);
	}
}
