package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.ColorDto;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.CreateColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.DeleteColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.UpdateColorRequest;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.entities.Color;

public interface ColorService {

	DataResult<List<ColorDto>> getAll();

	DataResult<ColorDto> getById(int colorId);

	Result add(CreateColorRequest createColorRequest);

	Result update(UpdateColorRequest updateColorRequest);

	Result delete(DeleteColorRequest deleteColorRequest);

	Result  checkIfColorIdExists(int colorId);

}
