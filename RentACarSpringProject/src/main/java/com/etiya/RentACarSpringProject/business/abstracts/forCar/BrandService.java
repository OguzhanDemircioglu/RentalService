package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.BrandDto;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.CreateBrandRequest;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.DeleteBrandRequest;
import com.etiya.RentACarSpringProject.business.requests.brandRequest.UpdateBrandRequest;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.entities.Brand;

public interface BrandService {
	
	DataResult<List<Brand>> findAll();

	DataResult<List<BrandDto>> getAll();
	
	DataResult<Brand> findById(int brandId);
	
	DataResult<BrandDto> getById(int brandId);
	
	Result add(CreateBrandRequest createBrandRequest);

	Result update(UpdateBrandRequest updateBrandRequest);

	Result delete(DeleteBrandRequest deleteBrandRequest);

	Result checkIfBrandIdExists(int brandId);
}
