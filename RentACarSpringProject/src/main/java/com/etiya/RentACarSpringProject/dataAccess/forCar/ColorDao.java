package com.etiya.RentACarSpringProject.dataAccess.forCar;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.Color;

public interface ColorDao extends JpaRepository<Color, Integer>{

	boolean existsByColorName(String colorName);
}

