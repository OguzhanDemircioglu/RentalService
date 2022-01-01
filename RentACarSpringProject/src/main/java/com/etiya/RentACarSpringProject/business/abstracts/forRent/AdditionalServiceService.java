package com.etiya.RentACarSpringProject.business.abstracts.forRent;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forRent.AdditionalServiceDto;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.AdditionalService;

public interface AdditionalServiceService {

	DataResult<List<AdditionalService>> findAll();

	DataResult<List<AdditionalServiceDto>> getAll();

	DataResult<AdditionalService> findById(int additionalServiceId);

	DataResult<AdditionalServiceDto> getById(int additionalServiceId);

	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest);

	Result checkIfAdditionalServiceIdExists(int AdditionalServiceId);
}
