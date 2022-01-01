package com.etiya.RentACarSpringProject.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler","rentals"})
@Table(name = "corporate_customers")
public class CorporateCustomer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "corporate_customer_id")
	private int corporateCustomerId;

	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "tax_number")
	private String taxNumber;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private ApplicationUser applicationUser;

}