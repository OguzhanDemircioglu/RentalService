package com.etiya.RentACarSpringProject.business.requests.creditCardRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

	@JsonIgnore
	private int creditCardId;

	private String creditCardNumber;

	private String expirationDate;

	private String cvc;

	private String nameOnCard;

	private int applicationUserUserUserId;
}