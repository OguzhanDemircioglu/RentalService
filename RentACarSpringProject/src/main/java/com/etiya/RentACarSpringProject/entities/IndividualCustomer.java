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
@Table(name = "individual_customers")
public class IndividualCustomer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "individual_customer_id")
	private int individualCustomerId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "identity_number")
	private String nationalIdentityNumber;

	@OneToOne()
	@JoinColumn(name = "user_id")
	private ApplicationUser applicationUser;

	
}
