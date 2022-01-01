package com.etiya.RentACarSpringProject.business.abstracts.forCar;

import java.util.List;

import com.etiya.RentACarSpringProject.business.dtos.forCar.RepairDto;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.CreateRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.DeleteRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.UpdateRepairRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.entities.Repair;

public interface RepairService {
	
	DataResult<List<Repair>> findAll();

	DataResult<List<RepairDto>> getAll();
	
	Result insert(CreateRepairRequest createRepairRequest);
	
	Result update(UpdateRepairRequest updateRepairRequest);

	Result delete(DeleteRepairRequest deleteRepairRequest);
	
}
