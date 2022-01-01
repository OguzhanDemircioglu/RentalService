package com.etiya.RentACarSpringProject.business.dtos.forRent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {

	private int creditCardId;

	private String nameOnCard;

	private String creditCardNumber;

	private String expirationDate;

	private String cvc;

}
