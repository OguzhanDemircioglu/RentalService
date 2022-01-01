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

import com.etiya.RentACarSpringProject.business.abstracts.forRent.AdditionalServiceService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.AdditionalServiceDto;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.CreateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.DeleteAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest.UpdateAdditionalServiceRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/additionalServices")
public class AdditionalServiceController {
	
	AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
		super();
		this.additionalServiceService = additionalServiceService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<AdditionalServiceDto>> getAll() {
		return this.additionalServiceService.getAll();
	}
	
	@GetMapping("/getById")
	public DataResult<AdditionalServiceDto> getById(int additionalServiceId) {
		return this.additionalServiceService.getById(additionalServiceId);
	}
	
	@PostMapping("/add")
	public Result add( @RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@PostMapping("/update")
	public Result update( @RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
}
