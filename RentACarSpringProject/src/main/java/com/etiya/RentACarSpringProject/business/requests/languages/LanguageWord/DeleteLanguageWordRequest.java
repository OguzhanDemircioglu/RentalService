package com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteLanguageWordRequest {

	@NotNull
	private int id;
}
