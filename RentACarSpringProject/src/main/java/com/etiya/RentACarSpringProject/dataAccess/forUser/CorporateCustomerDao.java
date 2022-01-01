package com.etiya.RentACarSpringProject.dataAccess.forUser;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.CorporateCustomer;

public interface CorporateCustomerDao extends JpaRepository<CorporateCustomer, Integer> {

	boolean existsByApplicationUser_UserId(int applicationUserId);
	
	CorporateCustomer getByApplicationUser_UserId(int applicationUserId);
}
