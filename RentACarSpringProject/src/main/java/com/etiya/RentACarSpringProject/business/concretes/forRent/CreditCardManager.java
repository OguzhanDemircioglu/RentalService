package com.etiya.RentACarSpringProject.business.concretes.forRent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forRent.CreditCardDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.CreditCardService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.CreditCardDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.CreditCard;

@Service
public class CreditCardManager implements CreditCardService {

	private CreditCardDao creditCardDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	@Autowired
	public CreditCardManager(CreditCardDao creditCardDao,UserService userService, ModelMapperService modelMapperService) {
		super();
		this.creditCardDao = creditCardDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public DataResult<List<CreditCard>> findAll() {

		return new SuccessDataResult<List<CreditCard>>(this.creditCardDao.findAll(), Messages.CreditCardsListed);
	}

	@Override
	public DataResult<List<CreditCardDto>> getAll() {

		List<CreditCard> creditCards = this.creditCardDao.findAll();
		List<CreditCardDto> creditCardsDto = creditCards.stream()
				.map(creditCard -> modelMapperService.forDto().map(creditCard, CreditCardDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CreditCardDto>>(creditCardsDto, Messages.CreditCardsListed);
	}

	@Override
	public DataResult<CreditCard> findById(int creditCardId) {

		var result= BusinessRules.run(checkIfCreditCardIdExists(creditCardId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<CreditCard>(this.creditCardDao.getById(creditCardId), Messages.GetCreditCard);
	}

	@Override
	public DataResult<CreditCardDto> getById(int creditCardId) {

		var result= BusinessRules.run(checkIfCreditCardIdExists(creditCardId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		CreditCard creditCard = this.creditCardDao.getById(creditCardId);
		return new SuccessDataResult<CreditCardDto>(modelMapperService.forDto().map(creditCard, CreditCardDto.class),
				Messages.GetCreditCard);
	}

	@Override
	public DataResult<List<CreditCard>> findCreditCardsByApplicationUser_UserId(int applicationUserId) {

		var result= BusinessRules.run(this.userService.checkIfUserIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<List<CreditCard>>(
				this.creditCardDao.getCreditCardByApplicationUser_UserId(applicationUserId),
				Messages.CreditCardsOfCustomerListed);
	}

	@Override
	public DataResult<List<CreditCardDto>> getCreditCardsByApplicationUser_UserId(int applicationUserId) {

		var result= BusinessRules.run(this.userService.checkIfUserIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		List<CreditCard> creditCards = this.creditCardDao.getCreditCardByApplicationUser_UserId(applicationUserId);

		List<CreditCardDto> creditCardsDto = creditCards.stream()
				.map(creditCard -> modelMapperService.forDto()
						.map(creditCard, CreditCardDto.class))
					.collect(Collectors.toList());

		return new SuccessDataResult<List<CreditCardDto>>(creditCardsDto, Messages.CreditCardsOfCustomerListed);

	}

	@Override
	public Result add(CreateCreditCardRequest createCreditCardRequest) {

		var result = BusinessRules.run(checkCreditCardNumber(createCreditCardRequest.getCreditCardNumber()),
				checkCreditCardCvv(createCreditCardRequest.getCvc()),
				checkCreditCardExpiryDate(createCreditCardRequest.getExpirationDate()),
				this.userService.checkIfUserIdExists(createCreditCardRequest.getApplicationUserUserUserId()));

		if (result != null) {
			return result;
		}

		CreditCard creditCard = modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CreditCardAdded);
	}

	@Override
	public Result update(UpdateCreditCardRequest updateCreditCardRequest) {


		var result = BusinessRules.run(checkCreditCardNumber(updateCreditCardRequest.getCreditCardNumber()),
				checkCreditCardCvv(updateCreditCardRequest.getCvc()),
				checkCreditCardExpiryDate(updateCreditCardRequest.getExpirationDate()),
				this.userService.checkIfUserIdExists(updateCreditCardRequest.getUserId()));

		if (result != null) {
			return result;
		}

		CreditCard creditCard = modelMapperService.forDto().map(updateCreditCardRequest, CreditCard.class);

		this.creditCardDao.save(creditCard);
		return new SuccessResult(Messages.CreditCardUpdated);
	}

	@Override
	public Result checkCreditCardFormat(String creditCardNumber, String cvc, String expirationDate) {
		return new SuccessResult();
	}
	@Override
	public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) {

		var result= BusinessRules.run(checkIfCreditCardIdExists(
				deleteCreditCardRequest.getCreditCardId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		CreditCard creditCard = creditCardDao.getById(deleteCreditCardRequest.getCreditCardId());
		this.creditCardDao.delete(creditCard);
		return new SuccessResult(Messages.CreditCardDeleted);
	}

	public Result checkCreditCardNumber(String cardNumber) {
		String regex = "((?:(?:\\d{4}[- ]){3}\\d{4}|\\d{16}))(?![\\d])";
		Pattern pattern = Pattern.compile(regex);
		java.util.regex.Matcher matcher = pattern.matcher(cardNumber);
		if (!matcher.matches()) {
			return new ErrorResult(Messages.InvalidCreditCardNumber);
		}
		return new SuccessResult();
	}

	public Result checkCreditCardCvv(String cvv) {
		String regex = "^[0-9]{3,4}$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(cvv);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.InvalidCreditCardCVVNumber);
		}
		return new SuccessResult();
	}

	public Result checkCreditCardExpiryDate(String expiryDate) {

		String regex = "(0[1-9]|1[0-2])/?(([0-9]{2}|[0-9]{2})$)";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(expiryDate);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.InvalidCreditExpireDate);
		}
		return new SuccessResult();
	}

	public Result  checkIfCreditCardIdExists(int creditCardId){

		var result=this.creditCardDao.existsById(creditCardId);
		if (!result){
			return new ErrorResult(Messages.NoCreditCard);
		}
		return new SuccessResult();
	}
}
