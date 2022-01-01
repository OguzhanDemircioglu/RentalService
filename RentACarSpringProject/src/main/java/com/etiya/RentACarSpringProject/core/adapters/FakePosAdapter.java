package com.etiya.RentACarSpringProject.core.adapters;

import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.core.fakeServices.FakePosService;
import com.etiya.RentACarSpringProject.core.webServices.PosService;

@Service
public class FakePosAdapter implements FakePosService {


	@Override
	public boolean fakePosService(String nameOnCard, String creditCardNumber, String expirationDate, String cvc, double price) {
		PosService posService = new PosService();
		return posService.fakePosService(nameOnCard, creditCardNumber, expirationDate, cvc, price);
	}

}

