package com.etiya.RentACarSpringProject.ws.forCar;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.RepairService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.RepairDto;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.CreateRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.DeleteRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.UpdateRepairRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

@RestController
@RequestMapping("api/repairs")
public class RepairController {
	RepairService repairService;

	@Autowired
	public RepairController(RepairService repairService) {
		super();
		this.repairService = repairService;
	}

	@GetMapping("/getAll")
	public DataResult<List<RepairDto>> getAll(){
		return this.repairService.getAll(); 
	}
	
	@PostMapping("/insert")
	public Result insert( @RequestBody @Valid CreateRepairRequest createRepairRequest){
		return this.repairService.insert(createRepairRequest); 
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateRepairRequest updateRepairRequest){
		return this.repairService.update(updateRepairRequest); 
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteRepairRequest deleteRepairRequest){
		return this.repairService.delete(deleteRepairRequest); 
	}	
}
