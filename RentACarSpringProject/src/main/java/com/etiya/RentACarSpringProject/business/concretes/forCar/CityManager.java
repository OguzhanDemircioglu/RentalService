package com.etiya.RentACarSpringProject.business.concretes.forCar;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CityService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CityDto;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.CreateCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.DeleteCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.UpdateCityRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forCar.CityDao;
import com.etiya.RentACarSpringProject.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    ModelMapperService modelMapperService;
    CityDao cityDao;

@Autowired
    public CityManager(ModelMapperService modelMapperService, CityDao cityDao) {
        this.modelMapperService = modelMapperService;
        this.cityDao = cityDao;
    }

    @Override
    public DataResult<List<CityDto>> getAll() {
        List<City> cities = this.cityDao.findAll();
        List<CityDto> citiesDto = cities.stream().map(city -> modelMapperService.forDto().map(city, CityDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CityDto>>(citiesDto, "Şehirler Listelendi");

    }

    @Override
    public DataResult<CityDto> getById(int cityId) {

        var result= BusinessRules.run(checkIfCityIdExists(cityId));
        if (result!=null){
            return  new ErrorDataResult(result);
        }

        City city= this.cityDao.getById(cityId);
        return new SuccessDataResult<CityDto>
                (modelMapperService.forDto().map(city, CityDto.class), "Messages.GetCity");
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {

      City city = modelMapperService.forDto().map(createCityRequest, City.class);
      this.cityDao.save(city);
        return new SuccessResult("Şehir eklendi");
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) {

        var result= BusinessRules.run(checkIfCityIdExists(updateCityRequest.getCityId()));
        if (result!=null){
            return  new ErrorDataResult(result);
        }

      City city = modelMapperService.forDto().map(updateCityRequest,City.class);
      this.cityDao.save(city);
        return new SuccessResult("Şehir güncellendi");
    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) {

        var result= BusinessRules.run(checkIfCityIdExists(deleteCityRequest.getCityId()));
        if (result!=null){
            return  new ErrorDataResult(result);
        }

        City city = modelMapperService.forDto().map(deleteCityRequest,City.class);
        this.cityDao.delete(city);
        return new SuccessResult("Şehir Silindi");
    }

    public Result  checkIfCityIdExists(int cityId){
        var result=this.cityDao.existsById(cityId);
        if (!result){
            return new ErrorResult("Böyle bir Şehir yok");
        }
        return new SuccessResult();
    }

}
