package com.etiya.RentACarSpringProject.business.requests.invoiceRequest;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.etiya.RentACarSpringProject.entities.Rental;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {

	@NotNull
    private Rental rental;

	private List<Integer> additionalService;
}
