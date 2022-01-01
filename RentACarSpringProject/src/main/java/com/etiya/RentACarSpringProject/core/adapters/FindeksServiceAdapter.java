package com.etiya.RentACarSpringProject.core.adapters;

import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.core.fakeServices.FindeksScoreService;
import com.etiya.RentACarSpringProject.core.webServices.FindeksService;

@Service
public class FindeksServiceAdapter implements FindeksScoreService{
	@Override
	public int getIndividualFindeksScore(String identityNumber) {
		FindeksService findeksScoreService = new FindeksService();
		return findeksScoreService.getIndividualFindeksScore(identityNumber);
	}

	@Override
	public int getCorporateFindeksScore(String taxNumber) {
		FindeksService findeksScoreService = new FindeksService();
		return findeksScoreService.getCorporateFindeksScore(taxNumber);
	}

}
