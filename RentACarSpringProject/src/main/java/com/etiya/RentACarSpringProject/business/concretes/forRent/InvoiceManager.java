package com.etiya.RentACarSpringProject.business.concretes.forRent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.fakeServices.FakePosService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forRent.InvoiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forRent.AdditionalServiceService;
import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.abstracts.forRent.InvoiceService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.InvoiceDto;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.DeleteInvoiceRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.AdditionalService;
import com.etiya.RentACarSpringProject.entities.Invoice;
import com.etiya.RentACarSpringProject.entities.Rental;

@Service
public class InvoiceManager implements InvoiceService {

	private InvoiceDao invoiceDao;

	private CarService carService;
	private AdditionalServiceService additionalServiceService;
	private FakePosService fakePosService;
	private ModelMapperService modelMapperService;

	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, CarService carService, AdditionalServiceService additionalServiceService,
						  FakePosService fakePosService, ModelMapperService modelMapperService) {
		this.invoiceDao = invoiceDao;
		this.carService = carService;
		this.additionalServiceService = additionalServiceService;
		this.fakePosService = fakePosService;
		this.modelMapperService = modelMapperService;
	}



	@Override
	public DataResult<List<Invoice>> findAll() {
		return new SuccessDataResult<List<Invoice>>(this.invoiceDao.findAll(), Messages.InvoicesListed);
	}

	@Override
	public DataResult<List<InvoiceDto>> getAll() {
		List<Invoice> invoices = this.invoiceDao.findAll();
		List<InvoiceDto> invoicesDto = invoices.stream().map(brand -> modelMapperService.forDto().map(brand, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto,  Messages.InvoicesListed);
	}

	@Override
	public DataResult<Invoice> findById(int invoiceId) {

		var result= BusinessRules.run(checkIfInvoiceIdExists(invoiceId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<Invoice>(this.invoiceDao.getById(invoiceId), Messages.GetInvoice);
	}

	@Override
	public DataResult<InvoiceDto> getById(int invoiceId) {

		var result= BusinessRules.run(checkIfInvoiceIdExists(invoiceId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		Invoice invoice = this.invoiceDao.getById(invoiceId);

		return new SuccessDataResult<InvoiceDto>(modelMapperService.forDto().map(invoice, InvoiceDto.class), Messages.GetInvoice);
	}

	@Override
	public DataResult<List<InvoiceDto>> findInvoicesBetween(Date endDate, Date startDate) {
		List<Invoice> invoices = this.invoiceDao.findAllByCreationDateBetween(endDate, startDate);
		List<InvoiceDto> invoicesDto = invoices.stream().map(invoice -> modelMapperService.forDto().map(invoice, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto,  Messages.BetweenDatesInvoices);
	}

	@Override
	public DataResult<List<InvoiceDto>> getByRental_ApplicationUser_UserId(int userId) {
		List<Invoice> invoices = this.invoiceDao.getByRental_ApplicationUser_UserId(userId);
		List<InvoiceDto> invoicesDto = invoices.stream().map(invoice -> modelMapperService.forDto().map(invoice, InvoiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<InvoiceDto>>(invoicesDto,  Messages.InvoicesOfCustomerListed);
	}

	@Override
	public Result insert(CreateInvoiceRequest createInvoiceRequest, List<Integer> additionalServices) {
		var result = BusinessRules.run(this.checkInvoiceByRentalId(createInvoiceRequest.getRental()
				.getRentalId()));

		if (result != null) {
			return result;
		}

		Invoice invoice = modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

		invoice.setAmount(invoiceAmountCalculation(additionalServices,
				createInvoiceRequest, 500));

		invoice.setInvoiceNo(java.util.UUID.randomUUID().toString());

		Date dateNow = new java.sql.Date(new java.util.Date().getTime());
		invoice.setCreationDate(dateNow);

		invoice.setRentDate(createInvoiceRequest.getRental().getRentDate());
		invoice.setReturnDate(createInvoiceRequest.getRental().getReturnDate());
		invoice.setTotalRentalDay(this.calculateTotalRentalDay(createInvoiceRequest));

		this.invoiceDao.save(invoice);

		return new SuccessResult(Messages.InvoiceAdded);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

		var result= BusinessRules.run(checkIfInvoiceIdExists(deleteInvoiceRequest.getInvoiceId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		Invoice invoice = this.invoiceDao.getById(deleteInvoiceRequest.getInvoiceId());

		this.invoiceDao.delete(invoice);
		return new SuccessResult(Messages.InvoiceDeleted);
	}

	private Result checkInvoiceByRentalId(int rentalId) {
		if (this.invoiceDao.existsByRental_RentalId(rentalId)) {
			return new ErrorResult(Messages.ERROR);
		}
		return new SuccessResult();
	}

	private double invoiceAmountCalculation(List<Integer> additionalServices, CreateInvoiceRequest createInvoiceRequest,
			int amountToRaisedIfReturnedAnotherCity) {
		double carDailyPrice = this.carService.getById(createInvoiceRequest.getRental().getCar().getCarId()).getData()
				.getDailyPrice();
		long totalRentalDay = this.calculateTotalRentalDay(createInvoiceRequest);

		if (this.checkReturnCity(createInvoiceRequest.getRental()).getMessage().contains("true")) {

			return carDailyPrice * totalRentalDay + amountToRaisedIfReturnedAnotherCity
					+ this.additionalServiceCost(additionalServices).getData();
		} else {
			return carDailyPrice * totalRentalDay + this.additionalServiceCost(additionalServices).getData();
		}
	}

	private long calculateTotalRentalDay(CreateInvoiceRequest createInvoiceRequest) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate rentDate = LocalDate.parse(createInvoiceRequest.getRental().getRentDate(), formatter);
		LocalDate returnDate = LocalDate.parse(createInvoiceRequest.getRental().getReturnDate(), formatter);

		return ChronoUnit.DAYS.between(rentDate, returnDate);
	}

	private DataResult<Integer> additionalServiceCost(List<Integer> additionalServicesId) {
		int totalCost = 0;

		List<AdditionalService> additionalServices = this.additionalServiceService.findAll().getData();
		for (int i = 0; i < additionalServicesId.size(); i++) {
			for (AdditionalService additionalService : additionalServices) {
				if (additionalService.getAdditionalServiceId() == this.additionalServiceService
						.getById(additionalServicesId.get(i)).getData().getAdditionalServiceId()) {
					totalCost = totalCost + additionalService.getPrice();
				}
			}
		}

		return new SuccessDataResult<Integer>(totalCost);
	}

	private Result checkReturnCity(Rental rental) {
		int returnCity = rental.getReturnCityId();
		int rentCity = rental.getRentCityId();

		if (returnCity!=rentCity) {
			return new SuccessResult("true");
		}
		return new SuccessResult("false");
	}

	private Result  checkIfInvoiceIdExists(int invoiceId){
		var result=this.invoiceDao.existsById(invoiceId);
		if (!result){
			return new ErrorResult(Messages.NoInvoice);
		}
		return new SuccessResult();
	}

}
