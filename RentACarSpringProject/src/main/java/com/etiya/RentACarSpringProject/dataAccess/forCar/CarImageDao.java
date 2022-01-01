package com.etiya.RentACarSpringProject.dataAccess.forCar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.CarImage;

public interface CarImageDao extends JpaRepository<CarImage, Integer> {
		
	int countByCar_CarId(int carId);
	
	List<CarImage> getByCar_CarId(int carId);
	
}
