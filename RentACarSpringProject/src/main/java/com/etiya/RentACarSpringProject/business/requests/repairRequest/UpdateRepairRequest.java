package com.etiya.RentACarSpringProject.business.requests.repairRequest;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRepairRequest {
	
	@NotNull
	private int repairId;
	
	@NotNull
	private int carId;
	
	@NotNull
	private String repairStartDate;
	
	private String repairFinishDate;
}