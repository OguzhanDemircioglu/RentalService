package com.etiya.RentACarSpringProject.business.requests.languages.Word;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteWordRequest {

	@NotNull
	private int wordId;
}
