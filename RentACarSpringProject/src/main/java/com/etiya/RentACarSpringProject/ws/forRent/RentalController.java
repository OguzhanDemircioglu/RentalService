package com.etiya.RentACarSpringProject.ws.forRent;

import java.util.List;

import javax.validation.Valid;

import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.UpdateCreditCardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.RentalService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.RentalDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.CreateRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.UpdateRentalRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/rentals")
public class RentalController {
	
	RentalService rentalService;

	@Autowired
	public RentalController(RentalService rentalService) {
		super();
		this.rentalService = rentalService;
	}

	@GetMapping("/getAll")
	public DataResult<List<RentalDto>> getAll() {
		return this.rentalService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<RentalDto> getById(int rentalId) {
		return this.rentalService.getById(rentalId);
	}

	@GetMapping("/existsByUserId")
	public Result existsByUserId(int applicationUserId) {
		return this.rentalService.existsByUserId(applicationUserId);
	}
	
	@PostMapping("/add")
	public Result insert(@RequestBody @Valid CreateRentalRequest createRentalRequest,@Valid CreateCreditCardRequest createCreditCardRequest) {
		return this.rentalService.insert(createRentalRequest, createCreditCardRequest);
	}

	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateRentalRequest updateRentalRequest,@Valid CreateCreditCardRequest createCreditCardRequest ) {
		return this.rentalService.update(updateRentalRequest, createCreditCardRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(DeleteRentalRequest deleteRentalRequest) {
		return this.rentalService.delete(deleteRentalRequest);
	}
	
}
