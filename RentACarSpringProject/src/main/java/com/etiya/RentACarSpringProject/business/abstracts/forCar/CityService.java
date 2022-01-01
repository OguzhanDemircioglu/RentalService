package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import com.etiya.RentACarSpringProject.business.dtos.forCar.CityDto;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.CreateCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.DeleteCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.UpdateCityRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

import java.util.List;

public interface CityService {

    DataResult<List<CityDto>> getAll();

    DataResult<CityDto> getById(int CityId);

    Result add(CreateCityRequest createCityRequest);

    Result update(UpdateCityRequest updateCityRequest);

    Result delete(DeleteCityRequest deleteCityRequest);

    Result  checkIfCityIdExists(int cityId);
}
