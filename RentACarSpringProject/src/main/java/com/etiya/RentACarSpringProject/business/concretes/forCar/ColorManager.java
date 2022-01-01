package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.dataAccess.forCar.ColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.ColorService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.ColorDto;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.CreateColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.DeleteColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.UpdateColorRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.entities.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;


	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		super();
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<Color>> findAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(), Messages.ColorsListed);
	}

	@Override
	public DataResult<List<ColorDto>> getAll() {
		List<Color> colors = this.colorDao.findAll();
		List<ColorDto> colorsDto = colors.stream().map(color -> modelMapperService.forDto().map(color, ColorDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<ColorDto>>(colorsDto, Messages.ColorsListed);
	}

	@Override
	public DataResult<Color> findById(int colorId) {

		var result = BusinessRules.run(checkIfColorIdExists(colorId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		return new SuccessDataResult<Color>(this.colorDao.getById(colorId),  Messages.GetColor);
	}

	@Override
	public DataResult<ColorDto> getById(int colorId) {

		var result = BusinessRules.run(checkIfColorIdExists(colorId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		Color color = this.colorDao.getById(colorId);
		return new SuccessDataResult<ColorDto>(modelMapperService.forDto().map(color, ColorDto.class), Messages.GetColor);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		var result = BusinessRules.run(this.checkIfColorNameExists(createColorRequest.getColorName()));

		if (result != null) {
			return result;
		}

		Color color = modelMapperService.forDto().map(createColorRequest, Color.class);

		this.colorDao.save(color);
		return new SuccessResult(Messages.ColorAdded);

	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {

		var result = BusinessRules.run(this.checkIfColorNameExists(updateColorRequest.getColorName()),
				checkIfColorIdExists(updateColorRequest.getColorId()));

		if (result != null) {
			return result;
		}

		Color color = modelMapperService.forDto().map(updateColorRequest, Color.class);

		this.colorDao.save(color);
		return new SuccessResult(Messages.ColorUpdated);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {

		var result= BusinessRules.run(checkIfColorIdExists(deleteColorRequest.getColorId()));
		if (result!=null){
			return  new ErrorDataResult(result);
		}
		Color color = this.colorDao.getById(deleteColorRequest.getColorId());

		this.colorDao.delete(color);
		return new SuccessResult( Messages.ColorDeleted);
	}

	private Result checkIfColorNameExists(String colorName) {
		if (this.colorDao.existsByColorName(colorName)) {
			return new ErrorResult(Messages.ExistColor);
		}
		return new SuccessResult();
	}

	public Result  checkIfColorIdExists(int colorId){
		var result=this.colorDao.existsById(colorId);
		if (!result){
			return new ErrorResult(Messages.NoColor);
		}
		return new SuccessResult();
	}
}
