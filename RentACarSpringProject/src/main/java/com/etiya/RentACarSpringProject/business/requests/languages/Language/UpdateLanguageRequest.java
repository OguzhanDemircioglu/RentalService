package com.etiya.RentACarSpringProject.business.requests.languages.Language;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLanguageRequest {

	private int languageId;

	private String languageName;
}
