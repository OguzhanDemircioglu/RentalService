package com.etiya.RentACarSpringProject.dataAccess.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACarSpringProject.entities.Car;

public interface CarDao extends JpaRepository<Car, Integer> {
	
	@Query("Select new com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail"
			+ "(c.carId, c.carName, b.brandName, cl.colorName, c.dailyPrice ) "
			+ 	"From Brand b Inner Join b.cars c Inner Join c.color cl ")

	//c.id c.carId olarak değişti
	
	List<CarwithBrandandColorDetail> getCarsWithDetails();
	
	List<Car> getByColor_ColorId(int colorId);

	List<Car> getByBrand_BrandId(int brandId);
	
	List<Car> findByInRepairFalse();
	
	List<Car> getByCity_CityId(int cityId);

}
