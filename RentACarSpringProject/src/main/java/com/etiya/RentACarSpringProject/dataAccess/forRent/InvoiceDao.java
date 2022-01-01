package com.etiya.RentACarSpringProject.dataAccess.forRent;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etiya.RentACarSpringProject.entities.Invoice;

public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

    boolean existsByRental_RentalId(int rentalId);

    List<Invoice> findAllByCreationDateBetween(Date endDate, Date startDate);

    List<Invoice> getByRental_ApplicationUser_UserId(int userId);
}
