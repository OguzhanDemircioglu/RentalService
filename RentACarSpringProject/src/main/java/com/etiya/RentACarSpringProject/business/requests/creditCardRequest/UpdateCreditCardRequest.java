package com.etiya.RentACarSpringProject.business.requests.creditCardRequest;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {

	@NotNull
	private int creditCardId;
	
	@NotNull
	private int userId;
	
	@NotNull
	private String creditCardNumber;

	@NotNull
	private String nameOnCard;
	
	@NotNull
	private String expirationDate;

	@NotNull
	private String cvc;
}
