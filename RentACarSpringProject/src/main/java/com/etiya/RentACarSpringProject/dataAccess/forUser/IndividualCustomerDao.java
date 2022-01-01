package com.etiya.RentACarSpringProject.dataAccess.forUser;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.IndividualCustomer;

public interface IndividualCustomerDao extends JpaRepository<IndividualCustomer, Integer> {
	
	boolean existsByApplicationUser_UserId(int applicationUserId);
	
	IndividualCustomer getByApplicationUser_UserId(int applicationUserId);
}
