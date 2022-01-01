package com.etiya.RentACarSpringProject.dataAccess.forRent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.CreditCard;

public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

    List<CreditCard> getCreditCardByApplicationUser_UserId(int applicationUserId);
}
