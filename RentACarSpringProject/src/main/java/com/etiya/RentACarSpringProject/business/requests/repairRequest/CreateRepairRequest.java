package com.etiya.RentACarSpringProject.business.requests.repairRequest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRepairRequest {
	
	@NotNull
	private int repairId;

	@JsonIgnore
	private String repairStartDate;

	@JsonIgnore
	private String repairFinishDate;
	
	@NotNull
	private int carId;
}
