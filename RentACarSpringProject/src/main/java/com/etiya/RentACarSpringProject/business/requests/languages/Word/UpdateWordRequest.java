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
public class UpdateWordRequest {

	@NotNull
	private int wordId;

	@NotNull
	private String key;
}
