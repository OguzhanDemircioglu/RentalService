package com.etiya.RentACarSpringProject.ws.forRent;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.CreditCardService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.CreditCardDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.DeleteCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.UpdateCreditCardRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/creditCards")
public class CreditCardController {

	CreditCardService creditCardService;

	@Autowired
	public CreditCardController(CreditCardService creditCardService) {
		super();
		this.creditCardService = creditCardService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CreditCardDto>> getAll() {
		return this.creditCardService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CreditCardDto> getById(int carInformationId) {
		return this.creditCardService.getById(carInformationId);
	}

	@GetMapping("/getCardInformationsByApplicationUser_UserId")
	DataResult<List<CreditCardDto>> getCardInformationsByApplicationUser_UserId(int applicationUserId) {
		return this.creditCardService.getCreditCardsByApplicationUser_UserId(applicationUserId);
	}

	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateCreditCardRequest createCardInformationRequest) {
		return this.creditCardService.add(createCardInformationRequest);
	}

	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateCreditCardRequest updateCardInformationRequest) {
		return this.creditCardService.update(updateCardInformationRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteCreditCardRequest deleteCardInformationRequest) {
		return this.creditCardService.delete(deleteCardInformationRequest);
	}

}
