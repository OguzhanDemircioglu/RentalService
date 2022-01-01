package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.business.requests.carRequest.UpdateCarRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.forCar.RepairDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.CarService;
import com.etiya.RentACarSpringProject.business.abstracts.forCar.RepairService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.RepairDto;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.CreateRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.DeleteRepairRequest;
import com.etiya.RentACarSpringProject.business.requests.repairRequest.UpdateRepairRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.entities.Repair;


@Service
public class RepairManager implements RepairService {

	private RepairDao repairDao;

	private CarService carService;
	private ModelMapperService modelMapperService;

	@Autowired
	public RepairManager(RepairDao repairDao, CarService carService, ModelMapperService modelMapperService) {
		super();
		this.repairDao = repairDao;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<Repair>> findAll() {
		return new SuccessDataResult<List<Repair>>(this.repairDao.findAll(), Messages.RepairsListed);
	}

	@Override
	public DataResult<List<RepairDto>> getAll() {
		List<Repair> repairs = this.repairDao.findAll();
		List<RepairDto> repairsDto = new ArrayList<RepairDto>();
		
		for (Repair repair : repairs) {
			RepairDto mappedRepairDto = modelMapperService.forDto().map(repair, RepairDto.class);
			
			repairsDto.add(mappedRepairDto);
		}		

		return new SuccessDataResult<List<RepairDto>>(repairsDto, Messages.RepairsListed);
	}

	@Override
	public Result insert(CreateRepairRequest createRepairRequest) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate rentDate = LocalDate.parse(createRepairRequest.getRepairStartDate(), formatter);


		Repair repair = modelMapperService.forRequest().map(createRepairRequest, Repair.class);
		this.setInRepairIfFinishDateIsNull(createRepairRequest.getCarId(), createRepairRequest.getRepairFinishDate());
		this.repairDao.save(repair);
		return new SuccessResult(Messages.RepairAdded);

	}

	@Override
	public Result update(UpdateRepairRequest updateRepairRequest) {

		var result= BusinessRules.run(checkIfRepairIdExists(updateRepairRequest.getRepairId()));
		if(result!=null){
			return  new ErrorDataResult(result);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate returnDate = LocalDate.parse(updateRepairRequest.getRepairFinishDate(), formatter);

		Repair repair = modelMapperService.forRequest().map(updateRepairRequest, Repair.class);

		this.setInRepairIfFinishDateIsNull(updateRepairRequest.getCarId(), updateRepairRequest.getRepairFinishDate());
		this.repairDao.save(repair);
		return new SuccessResult(Messages.RepairUpdated);
	}

	@Override
	public Result delete(DeleteRepairRequest deleteRepairRequest) {

		var result= BusinessRules.run(checkIfRepairIdExists(deleteRepairRequest.getRepairId()));

		if(result!=null){
			return  new ErrorDataResult(result);
		}

		this.repairDao.delete(this.repairDao.getById(deleteRepairRequest.getRepairId()));
		return new SuccessResult(Messages.RepairDeleted);
	}

	private Result setInRepairIfFinishDateIsNull(int carId, String repairFinishDate) {
		if (repairFinishDate==null ) {
			var result= this.carService.getById(carId).getData();
			UpdateCarRequest updateCarRequest=new UpdateCarRequest();
			updateCarRequest.setCarId(result.getCarId());
			updateCarRequest.setCarName(result.getCarName());
			updateCarRequest.setBrandId(result.getBrandId());
			updateCarRequest.setColorId(result.getColorId());
			updateCarRequest.setCityId(result.getCityId());
			updateCarRequest.setModelYear(result.getModelYear());
			updateCarRequest.setDailyPrice(result.getDailyPrice());
			updateCarRequest.setInRepair(true);
			updateCarRequest.setMinFindeksScore(result.getMinFindeksScore());
			updateCarRequest.setDescription(result.getDescription());
			updateCarRequest.setKm(result.getKm());

			this.carService.update(updateCarRequest);
			return new SuccessResult();
		} else {
			this.carService.getById(carId).getData().setInRepair(false);

			return new ErrorResult();
		}
	}

	private Result  checkIfRepairIdExists(int repairId){
		var result=this.repairDao.existsById(repairId);
		if (!result){
			return new ErrorResult(Messages.NoRepair);
		}
		return new SuccessResult();
	}


	private Result  checkIfStartDateIsTrue(String startDate){
		String regex = "^(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(?:(?:(\\/|-|\\.)(?:0?[13578]|1[02])\\1(?:31))|(?:(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2(?:29|30)))$|\n" +
				"^(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\\/|-|\\.)0?2\\3(?:29)$|\n" +
				"^(?:(?:1[6-9]|[2-9]\\d)?\\d{2})(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:0?[1-9]|1\\d|2[0-8])$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(startDate);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.RepairsStartDateIsFalse);
		}
		return new SuccessResult();
	}

	private Result  checkIfReturnDateIsTrue(String returnDate){
		String regex = "\\\\d{4}-[01]\\\\d-[0-3]\\\\d";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(returnDate);

		if (!matcher.matches()) {
			return new ErrorResult(Messages.RepairsFinishDateIsFalse);
		}
		return new SuccessResult();
	}
}
