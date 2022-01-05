package com.etiya.RentACarSpringProject.business.concretes.forUser;

import java.util.ArrayList;
import java.util.List;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forUser.IndividualCustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.IndividualCustomerService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.IndividualCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.CreateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,  Environment environment, LanguageWordService languageWordService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.environment=environment;
		this.languageWordService=languageWordService;
	}

	@Override
	public DataResult<List<IndividualCustomerDto>> getAll() {
		List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();
		List<IndividualCustomerDto> individualCustomersDto = new ArrayList<IndividualCustomerDto>();

		for (IndividualCustomer individualCustomer : individualCustomers) {
			IndividualCustomerDto mappedIndividualCustomer = modelMapperService.forDto().map(individualCustomer,
					IndividualCustomerDto.class);

			individualCustomersDto.add(mappedIndividualCustomer);
		}
        return new SuccessDataResult<List<IndividualCustomerDto>>(individualCustomersDto, languageWordService.getByLanguageAndKeyId(Messages.IndividualCustomerListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<IndividualCustomerDto> getById(int individualCustomerId) {

		var result= BusinessRules.run(checkIfIndividualCustomerIdExists(individualCustomerId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		IndividualCustomerDto mappedIndividualCustomer = modelMapperService.forDto()
				.map(this.individualCustomerDao.getById(individualCustomerId), IndividualCustomerDto.class);

        return new SuccessDataResult<IndividualCustomerDto>(mappedIndividualCustomer,languageWordService.getByLanguageAndKeyId(Messages.GetIndividualCustomer,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		IndividualCustomer individualCustomer = modelMapperService.forDto().map(createIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CustomerAdded,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		var result= BusinessRules.run(checkIfIndividualCustomerIdExists(
				updateIndividualCustomerRequest.getIndividualCustomerId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		IndividualCustomer individualCustomer = modelMapperService.forDto().map(updateIndividualCustomerRequest,
				IndividualCustomer.class);

		this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CustomerUpdated,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

		var result= BusinessRules.run(checkIfIndividualCustomerIdExists(
				deleteIndividualCustomerRequest.getIndividualCustomerId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		IndividualCustomer individualCustomer = this.individualCustomerDao
				.getById(deleteIndividualCustomerRequest.getIndividualCustomerId());

		this.individualCustomerDao.delete(individualCustomer);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.CustomerDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result existsByUserId(int applicationUserId) {

		var result= BusinessRules.run(checkIfIndividualCustomerIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		if (this.individualCustomerDao.existsByApplicationUser_UserId(applicationUserId)) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@Override
	public DataResult<IndividualCustomer> getByApplicationUser_UserId(int applicationUserId) {

		var result= BusinessRules.run(checkIfIndividualCustomerIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<IndividualCustomer>(

				this.individualCustomerDao.getByApplicationUser_UserId(applicationUserId));
	}

	private Result  checkIfIndividualCustomerIdExists(int individualCustomerId){
		var result=this.individualCustomerDao.existsById(individualCustomerId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoIndividualCustomer,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
