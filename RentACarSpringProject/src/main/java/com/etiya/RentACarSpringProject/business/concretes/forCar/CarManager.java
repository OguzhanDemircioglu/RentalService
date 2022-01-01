package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.util.ArrayList;
import java.util.List;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.BrandService;
import com.etiya.RentACarSpringProject.business.abstracts.forCar.CityService;
import com.etiya.RentACarSpringProject.business.abstracts.forCar.ColorService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.business.dtos.forCar.ColorDto;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forCar.CarDao;
import com.etiya.RentACarSpringProject.entities.complexTypies.CarwithBrandandColorDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDto;
import com.etiya.RentACarSpringProject.business.requests.carRequest.CreateCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.DeleteCarRequest;
import com.etiya.RentACarSpringProject.business.requests.carRequest.UpdateCarRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;

	private ModelMapperService modelMapperService;

	private ColorService colorService;
	private BrandService brandService;
	private CityService cityService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService,ColorService colorService,
					  BrandService brandService, CityService cityService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.colorService=colorService;
		this.brandService=brandService;
		this.cityService=cityService;
	}

	@Override
	public DataResult<List<Car>> findAll() {
		return new SuccessDataResult<List<Car>>(this.carDao.findAll(), Messages.CarsListed);
	}

	@Override
	public DataResult<List<CarDto>> getAll() {
		List<Car> cars = this.carDao.findAll();
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.CarsListed);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		var result= BusinessRules.run(
				colorService.checkIfColorIdExists(createCarRequest.getColorId()),
				brandService.checkIfBrandIdExists(createCarRequest.getBrandId()),
				cityService.checkIfCityIdExists(createCarRequest.getCityId()));

		if (result!=null){
			return  result;
		}


		Car car = modelMapperService.forDto().map(createCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.CarAdded);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		var result= BusinessRules.run(
				checkIfCarIdExists(updateCarRequest.getCarId()),
				colorService.checkIfColorIdExists(updateCarRequest.getColorId()),
				brandService.checkIfBrandIdExists(updateCarRequest.getBrandId()),
				cityService.checkIfCityIdExists(updateCarRequest.getCityId()));

		if (result!=null){
			return  new ErrorDataResult(result);
		}

		Car car = modelMapperService.forDto().map(updateCarRequest, Car.class);

		this.carDao.save(car);
		return new SuccessResult(Messages.CarUpdated);

	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {

		var result= BusinessRules.run(checkIfCarIdExists(deleteCarRequest.getCarId()));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		Car car = this.carDao.getById(deleteCarRequest.getCarId());

		this.carDao.delete(car);
		return new SuccessResult(Messages.CarDeleted);

	}

	@Override
	public DataResult<Car> findById(int carId) {

		var result= BusinessRules.run(checkIfCarIdExists(carId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}
		return new SuccessDataResult<Car>(this.carDao.getById(carId), Messages.GetCar);
	}

	@Override
	public DataResult<CarDto> getById(int carId) {

		var result= BusinessRules.run(checkIfCarIdExists(carId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		Car car = this.carDao.getById(carId);

		return new SuccessDataResult<CarDto>(modelMapperService.forDto().map(car, CarDto.class), Messages.GetCar);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByColorId(int colorId) {

		var result= BusinessRules.run(colorService.checkIfColorIdExists(colorId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		List<Car> cars = this.carDao.getByColor_ColorId(colorId);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.CarsListedByColor);
	}

	@Override
	public DataResult<List<CarDto>> getCarsByBrandId(int brandId) {

		var result= BusinessRules.run(brandService.checkIfBrandIdExists(brandId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		List<Car> cars = this.carDao.getByBrand_BrandId(brandId);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.CarsListedByBrand);
	}

	@Override
	public DataResult<List<CarDto>> getByCity(int cityId) {

		var result= BusinessRules.run(this.cityService.checkIfCityIdExists(cityId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		List<Car> cars = this.carDao.getByCity_CityId(cityId);
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(cars), Messages.CarsListedByCity);
	}

	@Override
	public DataResult<List<Car>> findAllAvailableCars() {
		return new SuccessDataResult<List<Car>>(this.carDao.findByInRepairFalse(), Messages.AvailableCarListed);
	}

	@Override
	public DataResult<List<CarDto>> getAllAvailableCars() {
		return new SuccessDataResult<List<CarDto>>(this.mappedCarList(this.carDao.findByInRepairFalse()),
				Messages.AvailableCarListed);
	}

	@Override
	public DataResult<List<CarwithBrandandColorDetail>> getCarsWithDetails() {
		return new SuccessDataResult<List<CarwithBrandandColorDetail>>(this.carDao.getCarsWithDetails(),
				Messages.CarDetailsListed);
	}


	private List<CarDto> mappedCarList(List<Car> cars) {
		List<CarDto> carsDto = new ArrayList<CarDto>();

		for (Car car : cars) {
			CarDto mappedCar = modelMapperService.forRequest().map(car, CarDto.class);

			carsDto.add(mappedCar);
		}
		return carsDto;
	}

	private CarDto mappedCar(Car car) {
		CarDto mappedCar = modelMapperService.forDto().map(car, CarDto.class);

		return mappedCar;
	}

	@Override
	public DataResult<List<Car>> findCarsByColorId(int colorId) {

		var result= BusinessRules.run(colorService.checkIfColorIdExists(colorId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<List<Car>>(this.carDao.getByColor_ColorId(colorId), Messages.CarsListedByColor);
	}

	@Override
	public DataResult<List<Car>> findCarsByBrandId(int brandId) {

		var result= BusinessRules.run(brandService.checkIfBrandIdExists(brandId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<List<Car>>(this.carDao.getByBrand_BrandId(brandId), Messages.CarsListedByBrand);
	}

	@Override
	public DataResult<List<Car>> findByCity(int cityId) {

		var result= BusinessRules.run(cityService.checkIfCityIdExists(cityId));
		if (result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<List<Car>>(this.carDao.getByCity_CityId(cityId), Messages.CarsListedByCity);
	}

	public Result  checkIfCarIdExists(int carId){
		var result=this.carDao.existsById(carId);
		if (!result){
			return new ErrorResult(Messages.NoCar);
		}
		return new SuccessResult();
	}

}

