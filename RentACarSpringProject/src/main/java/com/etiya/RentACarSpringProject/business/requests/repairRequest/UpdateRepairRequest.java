package com.etiya.RentACarSpringProject.business.requests.repairRequest;

import javax.validation.constraints.NotNull;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

	@JsonIgnore
	private String repairStartDate;
	@JsonIgnore
	private String repairFinishDate;
}