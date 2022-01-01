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

import com.etiya.RentACarSpringProject.business.abstracts.forCar.BrandService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.BrandDto;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.CreateBrandRequest;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.DeleteBrandRequest;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.UpdateBrandRequest;
import com.etiya.RentACarSpringProject.core.results.*;

@RestController
@RequestMapping("api/brands")
public class BrandController {
	
	BrandService brandService;

	@Autowired
	public BrandController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}
	
	@GetMapping("/getAll")
	public  DataResult<List<BrandDto>> getAll() {
		return this.brandService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<BrandDto> getById(int brandId) {
		return this.brandService.getById(brandId);
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateBrandRequest createBrandRequest) {
		return this.brandService.add(createBrandRequest);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
		return this.brandService.update(updateBrandRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		return this.brandService.delete(deleteBrandRequest);
	}
}
