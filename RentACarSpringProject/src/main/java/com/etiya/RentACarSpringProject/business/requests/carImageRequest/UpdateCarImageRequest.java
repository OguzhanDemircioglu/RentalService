package com.etiya.RentACarSpringProject.business.requests.carImageRequest;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarImageRequest {
	
	@NotNull
	private int carId;
	
	@NotNull
	private int carImageId;

	@NotNull
	private MultipartFile image;

}
