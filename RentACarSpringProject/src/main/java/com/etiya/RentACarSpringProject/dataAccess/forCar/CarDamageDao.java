package com.etiya.RentACarSpringProject.dataAccess.forCar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.CarDamage;

public interface CarDamageDao extends JpaRepository<CarDamage, Integer>{

	List<CarDamage> getByCar_CarId(int carId);
}
