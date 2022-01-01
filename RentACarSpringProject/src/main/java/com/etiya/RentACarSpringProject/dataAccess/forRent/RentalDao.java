package com.etiya.RentACarSpringProject.dataAccess.forRent;

import java.util.List;

import com.etiya.RentACarSpringProject.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalDao extends JpaRepository<Rental, Integer> {

    List<Rental> getByCar_CarId(int carId);

    boolean existsByApplicationUser_UserId(int applicationUserId);

    boolean existsByRentalId(int rentalId);
}
