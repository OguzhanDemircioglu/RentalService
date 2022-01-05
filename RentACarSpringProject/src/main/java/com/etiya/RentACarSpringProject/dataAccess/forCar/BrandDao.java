package com.etiya.RentACarSpringProject.dataAccess.forCar;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.Brand;

public interface BrandDao extends JpaRepository<Brand, Integer>{
	
	boolean existsByBrandName(String brandName);

}
