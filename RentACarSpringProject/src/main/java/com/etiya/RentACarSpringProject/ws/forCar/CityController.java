package com.etiya.RentACarSpringProject.ws.forCar;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CityService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.CityDto;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.CreateCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.DeleteCityRequest;
import com.etiya.RentACarSpringProject.business.requests.cityRequest.UpdateCityRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/city")
public class CityController {
    CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        super();
        this.cityService = cityService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CityDto>> getAll() {
        return this.cityService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<CityDto> getById(int cityId) {
        return this.cityService.getById(cityId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) {
        return this.cityService.add(createCityRequest);
    }

    @PostMapping("/update")
    public Result update( @RequestBody @Valid UpdateCityRequest updateCityRequest) {
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(DeleteCityRequest deleteCityRequest) {
        return this.cityService.delete(deleteCityRequest);
    }
}