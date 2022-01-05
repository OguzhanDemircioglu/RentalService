package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forCar.CarDamageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarDamageService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CarDamageDto;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.CreateCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.DeleteCarDamageRequest;
import com.etiya.RentACarSpringProject.business.requests.carDamageRequest.UpdateCarDamageRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.Brand;
import com.etiya.RentACarSpringProject.entities.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;
	
	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService,  Environment environment, LanguageWordService languageWordService) {
		super();
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
		this.environment = environment;
		this.languageWordService = languageWordService;
	
	}

	@Override
	public DataResult<List<CarDamageDto>> getAll() {
		
		List<CarDamage> carDamages = this.carDamageDao.findAll();

		List<CarDamageDto> carDamagesDto = carDamages.stream()
				.map(carDamage -> modelMapperService.forDto()
						.map(carDamage, CarDamageDto.class))
								.collect(Collectors.toList());
		
        return new SuccessDataResult<List<CarDamageDto>>(carDamagesDto, languageWordService.getByLanguageAndKeyId(Messages.DamageRecordsListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<List<CarDamageDto>> getByCarId(int carId) {

		var result = BusinessRules.run(checkIfCarIdExists(carId));

		if(result!=null){
			return new ErrorDataResult(result);
		}

		List<CarDamageDto> carDamagesDto = this.carDamageDao.getByCar_CarId(carId).stream()
				.map(carDamage -> modelMapperService.forDto()
						.map(carDamage, CarDamageDto.class))
								.collect(Collectors.toList());
		
        return new SuccessDataResult<List<CarDamageDto>>(carDamagesDto, languageWordService.getByLanguageAndKeyId(Messages.GetDamageRecordByCarId,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamageRequest) {


		CarDamage carDamage = modelMapperService.forDto().map(createCarDamageRequest, CarDamage.class);
		
		this.carDamageDao.save(carDamage);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.DamageRecordAdded,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {

		var result = BusinessRules.run(checkIfCarDamageIdExists
				(updateCarDamageRequest.getCarDamageId()));

		if(result!=null){
			return new ErrorDataResult(result);
		}

		CarDamage carDamage = modelMapperService.forDto()
				.map(updateCarDamageRequest, CarDamage.class);

		this.carDamageDao.save(carDamage);
		        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.DamageRecordUpdated,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) {

		var result = BusinessRules.run(checkIfCarDamageIdExists
				(deleteCarDamageRequest.getCarDamageId()));

		if(result!=null){
			return new ErrorDataResult(result);
		}

		CarDamage carDamage = this.carDamageDao.getById(deleteCarDamageRequest.getCarDamageId());

		this.carDamageDao.delete(carDamage);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.DamageRecordDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	private Result  checkIfCarDamageIdExists(int damageId){

		var result=this.carDamageDao.existsById(damageId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoDamage,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	private Result  checkIfCarIdExists(int carId){

		var result=this.carDamageDao.existsById(carId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoCar,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
