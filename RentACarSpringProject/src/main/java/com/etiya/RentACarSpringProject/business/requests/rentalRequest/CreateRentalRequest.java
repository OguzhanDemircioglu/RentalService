package com.etiya.RentACarSpringProject.business.requests.rentalRequest;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class CreateRentalRequest {

	@JsonIgnore
	private int rentalId;
	
	@NotNull
	private int carId;
	
	@NotNull
	private String rentDate;

	private String returnDate;
	
	@NotNull
	private int rentCityId;

	@NotNull
	private int returnCityId;
	
	@NotNull
	private int rentKm;

	private int returnKm;
	
	@NotNull
	private boolean returned;
	
	@NotNull
	private boolean saveCreditCard;
	

	private int applicationUserUserUserId;
	
	private List<Integer> additionalService;
	
}