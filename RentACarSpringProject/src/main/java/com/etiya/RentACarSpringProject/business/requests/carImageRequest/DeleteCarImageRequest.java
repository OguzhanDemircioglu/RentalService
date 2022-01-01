package com.etiya.RentACarSpringProject.business.requests.carImageRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarImageRequest {
	
	@NotNull
	private int carImageId;
	
}
