package com.etiya.RentACarSpringProject.business.requests.languages.Word;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWordRequest {


	@NotNull
	private String key;
	
}
