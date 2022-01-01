package com.etiya.RentACarSpringProject.core.webServices;


public class PosService {

	public boolean fakePosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc, Double price) {
		if(price>50000){
		return false;
		}
		return true;
	}
	
}
