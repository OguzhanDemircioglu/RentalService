package com.etiya.RentACarSpringProject.business.concretes.forRent;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forRent.AdditionalServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.AdditionalServiceService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.AdditionalServiceDto;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalService>> findAll() {

		return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(),
				Messages.AdditionalServicesListed);
	}

	@Override
	public DataResult<List<AdditionalServiceDto>> getAll() {

		return new SuccessDataResult<List<AdditionalServiceDto>>(
				this.additionalServiceDao.findAll().stream()
						.map(additionalService -> modelMapperService.forDto()
								.map(additionalService, AdditionalServiceDto.class))
				.collect(Collectors.toList()), Messages.AdditionalServicesListed);
	}

	@Override
	public DataResult<AdditionalService> findById(int additionalServiceId) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists(additionalServiceId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(additionalServiceId),
				Messages.GetAdditionalService);
	}

	@Override
	public DataResult<AdditionalServiceDto> getById(int additionalServiceId) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists(additionalServiceId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		return new SuccessDataResult<AdditionalServiceDto>(modelMapperService.forDto()
				.map(this.additionalServiceDao.getById(additionalServiceId), AdditionalServiceDto.class),
				Messages.GetAdditionalService);
	}
	
	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {

		var result = BusinessRules
				.run(this.checkIfAdditionalServiceNameExists(createAdditionalServiceRequest.getAdditionalServiceName()));

		if (result != null) {
			return result;
		}

		AdditionalService additionalService = modelMapperService.forDto()
				.map(createAdditionalServiceRequest, AdditionalService.class);

		this.additionalServiceDao.save(additionalService);

		return new SuccessResult(Messages.AdditionalServiceAdded);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists
				(updateAdditionalServiceRequest.getAdditionalServiceId()),
						this.checkIfAdditionalServiceNameExists
								(updateAdditionalServiceRequest.getAdditionalServiceName()));

		if (result != null) {
			return result;
		}

		AdditionalService additionalService = this.additionalServiceDao
				.getById(updateAdditionalServiceRequest.getAdditionalServiceId());

		additionalService.setAdditionalServiceName("");
		this.additionalServiceDao.save(additionalService);

		
		additionalService = modelMapperService.forDto()
				.map(updateAdditionalServiceRequest, AdditionalService.class);

		this.additionalServiceDao.save(additionalService);
		return new SuccessResult(Messages.AdditionalServiceUpdated);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists
				(deleteAdditionalServiceRequest.getAdditionalServiceId()));

		if (result != null) {
			return result;
		}

		AdditionalService additionalService = this.additionalServiceDao
				.getById(deleteAdditionalServiceRequest.getAdditionalServiceId());

		this.additionalServiceDao.delete(additionalService);
		return new SuccessResult(Messages.AdditionalServiceDeleted);
	}

	private Result checkIfAdditionalServiceNameExists(String additionalServiceName) {

		if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)) {
			return new ErrorResult(Messages.ExistAdditionService);
		}
		return new SuccessResult();
	}

	public Result checkIfAdditionalServiceIdExists(int AdditionalServiceId) {
		var result = this.additionalServiceDao.existsById(AdditionalServiceId);
		if (!result) {
			return new ErrorResult(Messages.NoAdditionalService);
		}
		return new SuccessResult();
	}
}
