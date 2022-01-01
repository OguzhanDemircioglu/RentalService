package com.etiya.RentACarSpringProject.business.requests.additionalServiceRequest;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdditionalServiceRequest {

	@NotNull
	private int additionalServiceId;
}
