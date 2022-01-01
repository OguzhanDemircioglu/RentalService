package com.etiya.RentACarSpringProject.business.concretes.forUser;

import java.util.ArrayList;
import java.util.List;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forUser.CorporateCustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.CorporateCustomerService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.CorporateCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.CreateCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.DeleteCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.corporateCustomerRequest.UpdateCorporateCustomerRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao,
									ModelMapperService modelMapperService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CorporateCustomer>> findAll() {
		return new SuccessDataResult<List<CorporateCustomer>>(this.corporateCustomerDao.findAll(), Messages.CorporateCustomerListed);
	}

	@Override
	public DataResult<List<CorporateCustomerDto>> getAll() {
		List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();
		List<CorporateCustomerDto> corporateCustomersDto = new ArrayList<CorporateCustomerDto>();

		for (CorporateCustomer corporateCustomer : corporateCustomers) {
			CorporateCustomerDto mappedCorporateCustomer = modelMapperService.forDto().map(corporateCustomer,
					CorporateCustomerDto.class);

			corporateCustomersDto.add(mappedCorporateCustomer);
		}
		return new SuccessDataResult<List<CorporateCustomerDto>>(corporateCustomersDto, Messages.CorporateCustomerListed);
	}

	@Override
	public DataResult<CorporateCustomer> findById(int corporateCustomerId) {
		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(corporateCustomerId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}
		return new SuccessDataResult<CorporateCustomer>(this.corporateCustomerDao.getById(corporateCustomerId), Messages.GetCorporateCustomer);
	}

	@Override
	public DataResult<CorporateCustomerDto> getById(int corporateCustomerId) {
		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(corporateCustomerId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}
		CorporateCustomerDto mappedCorporateCustomer = modelMapperService.forDto()
				.map(this.corporateCustomerDao.getById(corporateCustomerId), CorporateCustomerDto.class);

		return new SuccessDataResult<CorporateCustomerDto>(mappedCorporateCustomer, Messages.GetCorporateCustomer);
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = modelMapperService.forDto().map(createCorporateCustomerRequest, CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CustomerAdded);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(
				updateCorporateCustomerRequest.getCorporateCustomerId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		CorporateCustomer corporateCustomer = modelMapperService.forDto().map(updateCorporateCustomerRequest, CorporateCustomer.class);

		this.corporateCustomerDao.save(corporateCustomer);
		return new SuccessResult(Messages.CustomerUpdated);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) {

		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(
				deleteCorporateCustomerRequest.getCorporateCustomerId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}
		CorporateCustomer corporateCustomer = this.corporateCustomerDao
				.getById(deleteCorporateCustomerRequest.getCorporateCustomerId());

		this.corporateCustomerDao.delete(corporateCustomer);
		return new SuccessResult(Messages.CustomerDeleted);
	}

	@Override
	public Result existsByUserId(int applicationUserId) {

		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}
		if (this.corporateCustomerDao.existsByApplicationUser_UserId(applicationUserId)) {
			return new SuccessResult();
		}
		return new ErrorResult();
	}

	@Override
	public DataResult<CorporateCustomer> getByApplicationUser_UserId(int applicationUserId) {

		var result= BusinessRules.run(checkIfCorporateCustomerIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<CorporateCustomer>(
				this.corporateCustomerDao.getByApplicationUser_UserId(applicationUserId));
	}

	private Result  checkIfCorporateCustomerIdExists(int corporateCustomerId){

		var result=this.corporateCustomerDao.existsById(corporateCustomerId);
		if (!result){
			return new ErrorResult(Messages.NoCorporateCustomer);
		}
		return new SuccessResult();
	}
}
