package com.etiya.RentACarSpringProject.core.fakeServices;


public interface FakePosService {

	boolean fakePosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc, double price);
}
