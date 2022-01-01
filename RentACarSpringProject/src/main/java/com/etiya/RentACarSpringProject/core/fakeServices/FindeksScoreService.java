package com.etiya.RentACarSpringProject.core.fakeServices;

public interface FindeksScoreService {

	int getIndividualFindeksScore(String nationalIdentityNumber);

	int getCorporateFindeksScore(String taxNumber);

}
