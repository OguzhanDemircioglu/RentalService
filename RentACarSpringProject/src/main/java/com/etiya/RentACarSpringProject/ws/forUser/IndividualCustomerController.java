package com.etiya.RentACarSpringProject.ws.forUser;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.IndividualCustomerService;
import com.etiya.RentACarSpringProject.business.dtos.forUser.IndividualCustomerDto;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.DeleteIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.business.requests.individualCustomerRequest.UpdateIndividualCustomerRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/individualcustomers")
public class IndividualCustomerController {
	IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
		super();
		this.individualCustomerService = individualCustomerService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
	@GetMapping("/getbyid")
	public DataResult<IndividualCustomerDto> getById(int customerId) {
		return this.individualCustomerService.getById(customerId);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}
