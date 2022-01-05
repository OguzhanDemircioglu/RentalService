package com.etiya.RentACarSpringProject.core.business;

import com.etiya.RentACarSpringProject.core.results.Result;

public class BusinessRules {

	public static Result run(Result ...logics) {

		for (var logic : logics) {
			if (!logic.isSuccess()) {
				return logic;
			}
		}
		return null;
	}

}