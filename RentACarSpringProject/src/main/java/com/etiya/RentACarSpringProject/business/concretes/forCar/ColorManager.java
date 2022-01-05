package com.etiya.RentACarSpringProject.business.concretes.forCar;

import java.util.List;
import java.util.stream.Collectors;

import com.etiya.RentACarSpringProject.business.abstracts.forUser.UserService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.dataAccess.forCar.ColorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.etiya.RentACarSpringProject.business.abstracts.forCar.ColorService;
import com.etiya.RentACarSpringProject.business.dtos.forCar.ColorDto;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.CreateColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.DeleteColorRequest;
import com.etiya.RentACarSpringProject.business.requests.colorRequest.UpdateColorRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.entities.Car;
import com.etiya.RentACarSpringProject.entities.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	private Environment environment;
	private LanguageWordService languageWordService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService, Environment environment, LanguageWordService languageWordService) {
		super();
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
		this.environment = environment;
		this.languageWordService = languageWordService;
	}

	@Override
	public DataResult<List<ColorDto>> getAll() {
		List<Color> colors = this.colorDao.findAll();
		List<ColorDto> colorsDto = colors.stream().map(color -> modelMapperService.forDto().map(color, ColorDto.class))
				.collect(Collectors.toList());
        return new SuccessDataResult<List<ColorDto>>(colorsDto,languageWordService.getByLanguageAndKeyId(Messages.ColorsListed,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public DataResult<ColorDto> getById(int colorId) {

		var result = BusinessRules.run(checkIfColorIdExists(colorId));

		if (result != null) {
			return new ErrorDataResult(result);
		}

		Color color = this.colorDao.getById(colorId);
        return new SuccessDataResult<ColorDto>(modelMapperService.forDto().map(color, ColorDto.class),languageWordService.getByLanguageAndKeyId(Messages.GetColor,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {

		var result = BusinessRules.run(this.checkIfColorNameExists(createColorRequest.getColorName()));

		if (result != null) {
			return result;
		}

		Color color = modelMapperService.forDto().map(createColorRequest, Color.class);

		this.colorDao.save(color);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.ColorAdded,Integer.parseInt(environment.getProperty("language"))));

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
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.ColorUpdated,Integer.parseInt(environment.getProperty("language"))));

	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {

		var result= BusinessRules.run(checkIfColorIdExists(deleteColorRequest.getColorId()));
		if (result!=null){
			return  new ErrorDataResult(result);
		}
		Color color = this.colorDao.getById(deleteColorRequest.getColorId());

		this.colorDao.delete(color);
        return new SuccessResult(languageWordService.getByLanguageAndKeyId(Messages.ColorDeleted,Integer.parseInt(environment.getProperty("language"))));

	}

	private Result checkIfColorNameExists(String colorName) {
		if (this.colorDao.existsByColorName(colorName)) {
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.ExistColor,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}

	public Result  checkIfColorIdExists(int colorId){
		var result=this.colorDao.existsById(colorId);
		if (!result){
	        return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.NoColor,Integer.parseInt(environment.getProperty("language"))));

		}
		return new SuccessResult();
	}
}
