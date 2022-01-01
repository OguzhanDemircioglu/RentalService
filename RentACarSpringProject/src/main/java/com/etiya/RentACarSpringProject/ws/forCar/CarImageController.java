package com.etiya.RentACarSpringProject.ws.forCar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarImageService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarImageDto;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.CreateCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.DeleteCarImageRequest;
import com.etiya.RentACarSpringProject.business.requests.carImageRequest.UpdateCarImageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/carimages")
public class CarImageController {
	CarImageService carImageService;

	@Autowired
	public CarImageController(CarImageService carImageService) {
		super();
		this.carImageService = carImageService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarImageDto>> getAll() {
		return this.carImageService.getAll();
	}

	@GetMapping("/getImagesByCarId")
	public DataResult<List<CarImageDto>> getImagesByCarId(int carId) {
		return this.carImageService.getImagePathsByCarId(carId);
	}

	@PostMapping("/add")
	public Result add(@RequestParam("carId") int carId, MultipartFile file) {

		CreateCarImageRequest createCarImageRequest = new CreateCarImageRequest();
		createCarImageRequest.setCarId(carId);
		createCarImageRequest.setImage(file);

		return this.carImageService.add(createCarImageRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestParam("carId") int carId, @RequestParam("carImageId") int carImageId, @RequestParam("file")  MultipartFile file) {

		UpdateCarImageRequest updateCarImageRequest = new UpdateCarImageRequest();
		updateCarImageRequest.setCarId(carId);
		updateCarImageRequest.setCarImageId(carImageId);
		updateCarImageRequest.setImage(file);

		return this.carImageService.update(updateCarImageRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteCarImageRequest deleteCarImageRequest) {
		return this.carImageService.delete(deleteCarImageRequest);
	}
	
}
