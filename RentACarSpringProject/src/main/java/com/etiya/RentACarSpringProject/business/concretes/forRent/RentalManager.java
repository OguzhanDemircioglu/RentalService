package com.etiya.RentACarSpringProject.business.concretes.forRent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CityService;
import com.etiya.RentACarSpringProject.business.abstracts.forRent.AdditionalServiceService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorDataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
import com.etiya.RentACarSpringProject.dataAccess.forRent.RentalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.CorporateCustomerService;
import com.etiya.RentACarSpringProject.business.abstracts.forRent.CreditCardService;
import com.etiya.RentACarSpringProject.core.fakeServices.FakePosService;
import com.etiya.RentACarSpringProject.core.fakeServices.FindeksScoreService;
import com.etiya.RentACarSpringProject.business.abstracts.forUser.IndividualCustomerService;
import com.etiya.RentACarSpringProject.business.abstracts.forRent.InvoiceService;
import com.etiya.RentACarSpringProject.business.abstracts.forRent.RentalService;
import com.etiya.RentACarSpringProject.business.dtos.forRent.RentalDto;
import com.etiya.RentACarSpringProject.business.requests.creditCardRequest.CreateCreditCardRequest;
import com.etiya.RentACarSpringProject.business.requests.invoiceRequest.CreateInvoiceRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.CreateRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.DeleteRentalRequest;
import com.etiya.RentACarSpringProject.business.requests.rentalRequest.UpdateRentalRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.Car;
import com.etiya.RentACarSpringProject.entities.CorporateCustomer;
import com.etiya.RentACarSpringProject.entities.IndividualCustomer;
import com.etiya.RentACarSpringProject.entities.Rental;

@Service
public class RentalManager implements RentalService {

	private RentalDao rentalDao;

	private CityService cityService;
	private CarService carService;
	private CorporateCustomerService corporateCustomerService;
	private IndividualCustomerService individualCustomerService;
	private FindeksScoreService findeksScoreService;
	private CreditCardService creditCardService;
	private FakePosService fakePosService;
	private InvoiceService invoiceService;
	private UserService userService;
	private AdditionalServiceService additionalServiceService;
	private ModelMapperService modelMapperService;

	@Autowired
	public RentalManager(RentalDao rentalDao, CarService carService, CorporateCustomerService corporateCustomerService,
			IndividualCustomerService individualCustomerService, FindeksScoreService findeksScoreService,
			CreditCardService creditCardService, FakePosService fakePosService, InvoiceService invoiceService,
			 ModelMapperService modelMapperService,UserService userService,
						 AdditionalServiceService additionalServiceService,CityService cityService) {
		super();
		this.rentalDao = rentalDao;

		this.corporateCustomerService = corporateCustomerService;
		this.individualCustomerService = individualCustomerService;
		this.findeksScoreService = findeksScoreService;
		this.creditCardService = creditCardService;
		this.fakePosService = fakePosService;
		this.userService = userService;
		this.invoiceService = invoiceService;
		this.modelMapperService = modelMapperService;
		this.additionalServiceService = additionalServiceService;
		this.carService = carService;
		this.cityService=cityService;
	}

	@Override
	public DataResult<List<Rental>> findAll() {
		return new SuccessDataResult<List<Rental>>(this.rentalDao.findAll(), Messages.RentalsListed);
	}

	@Override
	public DataResult<List<RentalDto>> getAll() {
		List<Rental> rentals = this.rentalDao.findAll();
		List<RentalDto> rentalsDto = rentals.stream().map(brand -> modelMapperService.forDto().map(brand, RentalDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalDto>>(rentalsDto, Messages.RentalsListed);
	}

	@Override
	public Result insert(CreateRentalRequest createRentalRequest, CreateCreditCardRequest createCreditCardRequest) {

		var result = BusinessRules.run(
				this.carService.checkIfCarIdExists(createRentalRequest.getCarId()),
				checkIfCityExists(createRentalRequest.getReturnCityId()),
				checkIfCarInRepair(createRentalRequest),
				checkIfCarIsCurrentlyRented(createRentalRequest),
				checkRentCityByCarCity(createRentalRequest),
				checkCustomerFindeksScoreForCar(createRentalRequest),
				checkIsReturned(createRentalRequest),

				this.userService.checkIfUserIdExists(createRentalRequest.getApplicationUserUserUserId()),
				this.additionalServiceService.checkIfAdditionalServiceIdExists(createRentalRequest.getAdditionalService().get(0)),

				this.creditCardService.checkCreditCardExpiryDate(createCreditCardRequest.getExpirationDate()),
				this.creditCardService.checkCreditCardCvv(createCreditCardRequest.getCvc()),
				this.creditCardService.checkCreditCardNumber(createCreditCardRequest.getCreditCardNumber()),
				this.creditCardService.checkCreditCardFormat(createCreditCardRequest.getCreditCardNumber(),
				createCreditCardRequest.getCvc(), createCreditCardRequest.getExpirationDate())

				);

		if (result != null) {
			return new ErrorDataResult(result);
		}

		this.insertRental(createRentalRequest);
		this.saveCreditCard(createRentalRequest, createCreditCardRequest);

		return new SuccessResult(Messages.RentalAdded);
	}

	@Override
	public DataResult<Rental> findById(int rentalId) {

		var result=BusinessRules.run(checkIfRentalIdExists(rentalId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		return new SuccessDataResult<Rental>(rentalDao.getById(rentalId), Messages.GetRental);
	}

	@Override
	public DataResult<RentalDto> getById(int rentalId) {

		var result=BusinessRules.run(checkIfRentalIdExists(rentalId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		Rental rental = this.rentalDao.getById(rentalId);

		return new SuccessDataResult<RentalDto>(modelMapperService.forDto().map(rental, RentalDto.class),Messages.GetRental);
	}

	@Override
	public Result existsByUserId(int applicationUserId) {
		var result=BusinessRules.run(checkIfUserIdExists(applicationUserId));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		if (this.rentalDao.existsByApplicationUser_UserId(applicationUserId)) {
			Rental rental = this.rentalDao.getById(applicationUserId);

			return new SuccessDataResult<RentalDto>(modelMapperService.forDto().map(rental, RentalDto.class),Messages.GetUsersRental);
		}
		return new ErrorResult();
	}

	@Override
	public Result update(UpdateRentalRequest updateRentalRequest, CreateCreditCardRequest createCreditCardRequest) {

		CreateRentalRequest createRentalRequest = modelMapperService.forRequest().map(updateRentalRequest, CreateRentalRequest.class);

		var result = BusinessRules.run(
				this.carService.checkIfCarIdExists(createRentalRequest.getCarId()),
				checkIfCityExists(createRentalRequest.getReturnCityId()),
				checkIfCarInRepair(createRentalRequest),
				checkIfCarIsCurrentlyRented(createRentalRequest),
				checkRentCityByCarCity(createRentalRequest),
				checkCustomerFindeksScoreForCar(createRentalRequest),
				checkIsReturned(createRentalRequest),
				checkIfRentalIdExists(updateRentalRequest.getRentalId()),
				checkPosService(createCreditCardRequest,updateRentalRequest),

				this.userService.checkIfUserIdExists(createRentalRequest.getApplicationUserUserUserId()),
				this.additionalServiceService.checkIfAdditionalServiceIdExists(createRentalRequest.getAdditionalService().get(0)),
				this.creditCardService.checkCreditCardExpiryDate(createCreditCardRequest.getExpirationDate()),
				this.creditCardService.checkCreditCardCvv(createCreditCardRequest.getCvc()),
				this.creditCardService.checkCreditCardNumber(createCreditCardRequest.getCreditCardNumber()),
				this.creditCardService.checkCreditCardFormat(createCreditCardRequest.getCreditCardNumber(),
						createCreditCardRequest.getCvc(), createCreditCardRequest.getExpirationDate()));

		if (result != null) {
			return result;
		}

		this.insertRental(createRentalRequest);
		this.saveCreditCard(createRentalRequest, createCreditCardRequest);
		return new SuccessResult(Messages.RentalUpdated);
	}

	@Override
	public Result delete(DeleteRentalRequest deleteRentalRequest) {

		var result=BusinessRules.run(checkIfRentalIdExists(deleteRentalRequest.getRentalId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		this.rentalDao.delete(rentalDao.getById(deleteRentalRequest.getRentalId()));
		return new SuccessResult(Messages.RentalDeleted);
	}

	private Result checkIfCarInRepair(CreateRentalRequest createRentalRequest) {
		if (carService.getById(createRentalRequest.getCarId()).getData().isInRepair()) {
			return new ErrorResult(Messages.CarInRepair);
		}
		return new SuccessResult();
	}

	private Result checkIfCarIsCurrentlyRented(CreateRentalRequest createRentalRequest) {

		for (Rental rental : this.rentalDao.getByCar_CarId(createRentalRequest.getCarId())) {
			if (!rental.isReturned()) {
				return new ErrorResult(Messages.CarIsnotAvailable);
			}
		}
		return new SuccessResult();
	}

	private Result checkRentCityByCarCity(CreateRentalRequest createRentalRequest) {
		int rentCity = createRentalRequest.getRentCityId();
		int carCity = this.carService.getById(createRentalRequest.getCarId()).getData().getCityId();
		if (rentCity!=carCity) {
			return new ErrorResult(Messages.CarIsInDiffirentCity);
		}
		return new SuccessResult();
	}

	private Result checkCustomerFindeksScoreForCar(CreateRentalRequest createRentalRequest) {

		if (this.individualCustomerService.existsByUserId(createRentalRequest.getApplicationUserUserUserId())
				.isSuccess()) {
			IndividualCustomer individualCustomer = this.individualCustomerService
					.getByApplicationUser_UserId(createRentalRequest.getApplicationUserUserUserId()).getData();

			if (this.carService.getById(createRentalRequest.getCarId()).getData()
					.getMinFindeksScore() > this.findeksScoreService
							.getIndividualFindeksScore(individualCustomer.getNationalIdentityNumber())) {

				return new ErrorResult(Messages.CustomerCreditScoreNotEnoughtToRentCar);
			}

		}

		if (this.corporateCustomerService.existsByUserId(createRentalRequest.getApplicationUserUserUserId()).isSuccess()) {

			CorporateCustomer corporateCustomer = this.corporateCustomerService.getByApplicationUser_UserId(createRentalRequest
					.getApplicationUserUserUserId()).getData();

			if (this.carService.getById(createRentalRequest.getCarId()).getData()
					.getMinFindeksScore() > this.findeksScoreService
							.getCorporateFindeksScore(corporateCustomer.getTaxNumber())) {

				return new ErrorResult(Messages.CustomerCreditScoreNotEnoughtToRentCar);
			}
		}

		return new SuccessResult();

	}

public Result checkPosService(CreateCreditCardRequest createCreditCardRequest, CreateRentalRequest createRentalRequest) {

		var carDailyPrice =this.carService.getById(createRentalRequest.getCarId()).getData().getDailyPrice();
	System.out.println(carDailyPrice);
		var  totalDay= calculateTotalRentalDay(createRentalRequest);
	System.out.println(totalDay);
		double totalAmount=carDailyPrice*totalDay;
	System.out.println(totalAmount);
		if (this.fakePosService.fakePosService(createCreditCardRequest.getNameOnCard(),
				createCreditCardRequest.getCreditCardNumber(), createCreditCardRequest.getExpirationDate(),
				createCreditCardRequest.getCvc(),totalAmount)) {
			return new SuccessResult();
		} else {
			return new ErrorResult(Messages.PaymentIsNOTReceived);
		}
	}


	public Result checkPosService(CreateCreditCardRequest createCreditCardRequest, UpdateRentalRequest updateRentalRequest) {

		var carDailyPrice =this.carService.getById(updateRentalRequest.getCarId()).getData().getDailyPrice();
		System.out.println(carDailyPrice);
		var  totalDay= calculateTotalRentalDay(updateRentalRequest);
		System.out.println(totalDay);
		double totalAmount=carDailyPrice*totalDay;
		System.out.println(totalAmount);
		if (this.fakePosService.fakePosService(createCreditCardRequest.getNameOnCard(),
				createCreditCardRequest.getCreditCardNumber(), createCreditCardRequest.getExpirationDate(),
				createCreditCardRequest.getCvc(),totalAmount)) {
			return new SuccessResult();
		} else {
			return new ErrorResult(Messages.PaymentIsNOTReceived);
		}
	}

	private long calculateTotalRentalDay(CreateRentalRequest createRentalRequest) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate rentDate = LocalDate.parse(createRentalRequest.getRentDate(), formatter);
		LocalDate returnDate = LocalDate.parse(createRentalRequest.getReturnDate(), formatter);

		return ChronoUnit.DAYS.between(rentDate, returnDate);
	}

	private long calculateTotalRentalDay(UpdateRentalRequest updateRentalRequest) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate rentDate = LocalDate.parse(updateRentalRequest.getRentDate(), formatter);
		LocalDate returnDate = LocalDate.parse(updateRentalRequest.getReturnDate(), formatter);

		return ChronoUnit.DAYS.between(rentDate, returnDate);
	}

	private Result checkIsReturned(CreateRentalRequest createRentalRequest) {

		if (createRentalRequest.isReturned()) {

			this.checkReturnCity(createRentalRequest);
			this.setCarReturnKm(createRentalRequest);

			return new SuccessResult();

		} else {
			return new SuccessResult();
		}
	}

	private Result checkReturnCity(CreateRentalRequest createRentalRequest) {
		int returnCity = createRentalRequest.getReturnCityId();
		int rentCity = createRentalRequest.getRentCityId();

		if (returnCity!=rentCity) {
			this.carService.findById(createRentalRequest.getCarId()).getData().getCity();
			return new SuccessResult("true");
		}
		return new ErrorResult("false");
	}

	private Result setCarReturnKm(CreateRentalRequest createRentalRequest) {

		Car car = this.carService.findById(createRentalRequest.getCarId()).getData();

		if (createRentalRequest.getReturnKm() > createRentalRequest.getRentKm()) {
			car.setKm(createRentalRequest.getReturnKm());
		}
		return new SuccessResult();
	}

	private Result insertRental(CreateRentalRequest createRentalRequest) {

		Rental rental = modelMapperService.forRequest().map(createRentalRequest, Rental.class);
		this.rentalDao.save(rental);

		this.createRentalInvoiceRequest(createRentalRequest, rental);

		return new SuccessResult(Messages.RentalAdded);
	}

	public DataResult<CreateInvoiceRequest> createRentalInvoiceRequest(CreateRentalRequest createRentalRequest,
			Rental rental) {

		if (createRentalRequest.isReturned()) {
			CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
			createInvoiceRequest.setAdditionalService(createRentalRequest.getAdditionalService());
			createInvoiceRequest.setRental(rental);

			this.invoiceService.insert(createInvoiceRequest, createInvoiceRequest.getAdditionalService());
			return new SuccessDataResult<CreateInvoiceRequest>(createInvoiceRequest);
		}
		return new SuccessDataResult<CreateInvoiceRequest>();

	}

	private void saveCreditCard(CreateRentalRequest createRentalRequest,
			CreateCreditCardRequest createCreditCardRequest) {
		if (createRentalRequest.isSaveCreditCard()) {
			this.creditCardService.add(createCreditCardRequest);
		}
	}

	private Result  checkIfRentalIdExists(int rentalId){
		var result=this.rentalDao.existsByRentalId(rentalId);
		if (!result){
			return new ErrorResult(Messages.NoRental);
		}
		return new SuccessResult();
	}

	private Result  checkIfUserIdExists(int userId){
		var result=this.rentalDao.existsByApplicationUser_UserId(userId);
		if (!result){
			return new ErrorResult(Messages.NoCustomer);
		}
		return new SuccessResult();
	}
	private Result  checkIfCarExists(int carId){
		var result=this.carService.checkIfCarIdExists(carId);
		if (!result.isSuccess()){
			return new ErrorResult(Messages.NoCar);
		}
		return new SuccessResult();
	}
	private Result  checkIfCityExists(int cityId){
		var result=this.cityService.checkIfCityIdExists(cityId);
		if (!result.isSuccess()){
			return new ErrorResult("Şehir yok");
		}
		return new SuccessResult();
	}
}