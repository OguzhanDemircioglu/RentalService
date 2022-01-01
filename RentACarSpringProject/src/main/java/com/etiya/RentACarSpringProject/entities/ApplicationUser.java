package com.etiya.RentACarSpringProject.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler","rentals","individualCustomer","corporateCustomer"})
@Table(name = "users")
public class ApplicationUser extends User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@OneToOne(mappedBy = "applicationUser", cascade = CascadeType.ALL)
	private IndividualCustomer individualCustomer;
	
	@OneToOne(mappedBy = "applicationUser")
	private CorporateCustomer corporateCustomer;

	@JsonIgnore
	@OneToMany(mappedBy = "applicationUser")
	private List<Rental> rentals;
	
	@JsonIgnore
	@OneToMany(mappedBy = "applicationUser")
	private List<CreditCard> creditCard;
	
}