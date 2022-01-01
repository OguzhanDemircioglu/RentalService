package com.etiya.RentACarSpringProject.business.requests.repairRequest;


import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class DeleteRepairRequest {

	@NotNull
	private int repairId;
}
