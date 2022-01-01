package com.etiya.RentACarSpringProject.dataAccess.forCar;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.Repair;

public interface RepairDao extends JpaRepository<Repair, Integer> {

}
