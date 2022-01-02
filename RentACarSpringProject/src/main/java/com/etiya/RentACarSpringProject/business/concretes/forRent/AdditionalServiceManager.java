package com.etiya.RentACarSpringProject.business.concretes.forRent;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forRent.AdditionalServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.AdditionalServiceService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.AdditionalServiceDto;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.AdditionalService;
import com.etiya.RentACarSpringProject.entities.Repair;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService,  Environment environment, LanguageWordService languageWordService) {
		super();
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
		this.environment=environment;
		this.languageWordService=languageWordService;
	}

	@Override
	public DataResult<List<AdditionalService>> findAll() {

        return new SuccessDataResult<List<AdditionalService>>(this.additionalServiceDao.findAll(),languageWordService.getByLanguageAndKeyId(Messages.AdditionalServicesListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<List<AdditionalServiceDto>> getAll() {
	
        return new SuccessDataResult<List<AdditionalServiceDto>>(this.additionalServiceDao.findAll().stream()
				.map(additionalService -> modelMapperService.forDto()
						.map(additionalService, AdditionalServiceDto.class))
							.collect(Collectors.toList()),languageWordService.getByLanguageAndKeyId(Messages.AdditionalServicesListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<AdditionalService> findById(int additionalServiceId) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists(additionalServiceId));

		if (result != null) {
			return new ErrorDataResult(result);
		}
		
        return new SuccessDataResult<AdditionalService>(this.additionalServiceDao.getById(additionalServiceId),languageWordService.getByLanguageAndKeyId(Messages.GetAdditionalService,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<AdditionalServiceDto> getById(int additionalServiceId) {

		var result = BusinessRules.run(checkIfAdditionalServiceIdExists(additionalServiceId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

        return new SuccessDataResult<AdditionalServiceDto>(modelMapperService.forDto()
				.map(this.additionalServiceDao.getById(additionalServiceId), AdditionalServiceDto.class),languageWordService
					.getByLanguageAndKeyId(Messages.GetAdditionalService,Integer.parseInt(environment.getProperty("language"))));

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

        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.AdditionalServiceAdded,Integer.parseInt(environment.getProperty("language"))));

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
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.AdditionalServiceUpdated,Integer.parseInt(environment.getProperty("language"))));

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
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.AdditionalServiceDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	private Result checkIfAdditionalServiceNameExists(String additionalServiceName) {

		if (this.additionalServiceDao.existsByAdditionalServiceName(additionalServiceName)) {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.ExistAdditionService,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	public Result checkIfAdditionalServiceIdExists(int AdditionalServiceId) {
		var result = this.additionalServiceDao.existsById(AdditionalServiceId);
		if (!result) {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoAdditionalService,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
