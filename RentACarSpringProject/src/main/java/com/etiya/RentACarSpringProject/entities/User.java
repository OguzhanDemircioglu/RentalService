package com.etiya.RentACarSpringProject.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
@MappedSuperclass
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;

	@NotBlank
	@NotNull
	@Column(name = "email")
	private String email;

	@NotBlank
	@NotNull
	@Column(name = "password")
	private String password;
}
