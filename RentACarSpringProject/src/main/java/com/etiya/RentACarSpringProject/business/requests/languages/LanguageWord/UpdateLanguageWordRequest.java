package com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLanguageWordRequest {


	@NotNull
	private int id;

	@NotNull
	private int wordId;

	@NotNull
	private int languageId;

	@NotNull
	private String translation;
}
