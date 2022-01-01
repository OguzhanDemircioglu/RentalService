package com.etiya.RentACarSpringProject.business.requests.languages.Language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateLanguageRequest {

	@NotNull
	
	private String name;
	
}
