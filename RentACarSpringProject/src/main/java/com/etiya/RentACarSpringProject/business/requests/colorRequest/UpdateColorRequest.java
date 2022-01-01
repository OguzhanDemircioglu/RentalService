package com.etiya.RentACarSpringProject.business.requests.colorRequest;

import javax.validation.constraints.NotNull;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {
	
	@NotNull
	private int colorId;

	@NotNull
	private String colorName;
}
