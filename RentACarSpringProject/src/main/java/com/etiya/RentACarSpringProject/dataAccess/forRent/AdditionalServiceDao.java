package com.etiya.RentACarSpringProject.dataAccess.forRent;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.AdditionalService;

public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer> {

    boolean existsByAdditionalServiceName(String name);

}

