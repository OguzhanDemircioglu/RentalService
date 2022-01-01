package com.etiya.RentACarSpringProject.business.abstracts.forRent;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forRent.CreditCardDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.CreditCard;

public interface CreditCardService {
	
	DataResult<List<CreditCard>> findAll();

	DataResult<List<CreditCardDto>> getAll();

	DataResult<CreditCard> findById(int cardInformationId); 

	DataResult<CreditCardDto> getById(int cardInformationId); 
	
	DataResult<List<CreditCard>> findCreditCardsByApplicationUser_UserId(int applicationUserId);

	DataResult<List<CreditCardDto>> getCreditCardsByApplicationUser_UserId(int applicationUserId);

	Result add(CreateCreditCardRequest createCreditCardRequest);

	Result update(UpdateCreditCardRequest updateCreditCardRequest);

	Result delete(DeleteCreditCardRequest deleteCreditCardRequest);

	Result checkCreditCardExpiryDate(String expiryDate);
	Result checkCreditCardCvv(String cvv);
	Result checkCreditCardNumber(String cardNumber);
	Result  checkIfCreditCardIdExists(int creditCardId);
	Result checkCreditCardFormat(String creditCardNumber, String cvc,String expirationDate);
}
