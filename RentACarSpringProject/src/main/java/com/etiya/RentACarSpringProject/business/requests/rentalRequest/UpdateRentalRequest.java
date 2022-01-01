package com.etiya.RentACarSpringProject.business.requests.rentalRequest;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalRequest {

	@NotNull
	private int rentalId;
	
	@NotNull
	private int carId;
	
	@NotNull
	private int applicationUserUserUserId;

	@NotNull
	private int rentCityId;

	@NotNull
	private int returnCityId;

	@NotNull
	private String rentDate;

	@NotNull
	private String returnDate;

	@NotNull
	private String rentKm;
	
	@NotNull
	private String returnKm;
	
	@NotNull
	private boolean saveCreditCard;
	
	@NotNull
	private boolean returned;
	
	private List<Integer> additionalService;
}
