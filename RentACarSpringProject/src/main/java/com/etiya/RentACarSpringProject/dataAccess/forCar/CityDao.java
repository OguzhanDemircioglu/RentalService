package com.etiya.RentACarSpringProject.dataAccess.forCar;

import com.etiya.RentACarSpringProject.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository <City,Integer>{
}
