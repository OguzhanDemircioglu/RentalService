package com.etiya.RentACarSpringProject.dataAccess.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.entities.complexTypies.AllAvaibleCars;
import com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.etiya.RentACarSpringProject.entities.Car;

public interface CarDao extends JpaRepository<Car, Integer> {
	
	@Query("Select new com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail"
			+ "(c.carId, c.carName, b.brandName, cl.colorName, c.dailyPrice ) "
			+ 	"From Brand b Inner Join b.cars c Inner Join c.color cl ")

	
	List<CarwithBrandandColorDetail> getCarsWithDetails();
	
	List<Car> getByColor_ColorId(int colorId);

	List<Car> getByBrand_BrandId(int brandId);
	
	List<Car> findByInRepairFalse();
	
	List<Car> getByCity_CityId(int cityId);

	@Query("Select new com.etiya.RentACarSpringProject.entities.complexTypies.AllAvaibleCars"
		    + "(c.carId, c.carName, c.modelYear, c.dailyPrice, c.description,c.city.cityId, c.inRepair, c.brand.brandId, c.color.colorId,r.returned) "
		    +"from  Car c left join Rental r on r.car.carId= c.carId "
		            +"inner join Color co on co.colorId=c.color.colorId "
		            +"inner join Brand br on br.brandId=c.brand.brandId "
		            +"inner join City ct on ct.cityId=c.city.cityId "
		            +"inner join Repair rp on rp.car.carId=c.carId where r.returned=true and c.inRepair=false group by c.carId, c.carName, c.modelYear, c.dailyPrice, c.description,c.city.cityId, c.inRepair, c.brand.brandId, c.color.colorId,r.returned")
		    List<AllAvaibleCars> getAvaibleCars();
}
