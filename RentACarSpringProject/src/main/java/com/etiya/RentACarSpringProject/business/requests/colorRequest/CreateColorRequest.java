package com.etiya.RentACarSpringProject.business.requests.colorRequest;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {
	@JsonIgnore
	private int colorId;
	@NotNull
	private String colorName;
}
