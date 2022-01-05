package com.etiya.RentACarSpringProject.business.concretes.languages;


import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.CreateLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.DeleteLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.UpdateLanguageRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.languages.LanguageDao;
import com.etiya.RentACarSpringProject.entities.languages.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageManager implements LanguageService {

	private LanguageDao languageDao;
    private ModelMapperService modelMapperService;
    private Environment environment;
    private LanguageWordService languageWordService;

    @Autowired
    public LanguageManager(LanguageDao languageDao, ModelMapperService modelMapperService, Environment environment, @Lazy LanguageWordService languageWordService) {
        this.languageDao = languageDao;
        this.modelMapperService=modelMapperService;
        this.environment=environment;
        this.languageWordService=languageWordService;
    }

    @Override
    public DataResult<List<LanguageDto>> getAll() {
        List<Language> result = this.languageDao.findAll();
        List<LanguageDto> response = result.stream()
                .map(language -> modelMapperService.forDto().map(language, LanguageDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<LanguageDto>>(response);
    }

    @Override
    public Result save(@Valid CreateLanguageRequest createLanguageRequest) {

        var result = BusinessRules.run(
                checkIfLanguageNameExists(createLanguageRequest.getName()));

        if (result != null) {
            return new ErrorDataResult(result);
        }

        Language language = modelMapperService.forRequest().map(createLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult();

    }

    @Override
    public Result update(@Valid UpdateLanguageRequest updateLanguageRequest) {

        var result = BusinessRules.run(checkIfLanguageIdExists(updateLanguageRequest.getLanguageId()),
                checkIfLanguageNameExists(updateLanguageRequest.getLanguageName()));

        if (result != null) {
            return new ErrorDataResult(result);
        }

        Language language = modelMapperService.forRequest().map(updateLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult();
    }

    @Override
    public Result delete(@Valid DeleteLanguageRequest deleteLanguageRequest) {

        var result = BusinessRules.run(checkIfLanguageIdExists(deleteLanguageRequest.getLanguageId()));

        if (result != null) {
            return new ErrorDataResult(result);
        }

        this.languageDao.deleteById(deleteLanguageRequest.getLanguageId());
        return new SuccessResult();
    }
    
    public Result checkLanguageExists(int languageId) {
        if (this.languageDao.existsById(languageId)) {
            return new SuccessResult();
        } else if (Integer.parseInt(this.environment.getProperty("language")) != languageId) {
            languageId=1;
            return new SuccessResult();
        } else {
            return new ErrorResult();
        }
    }

    public Result  checkIfLanguageIdExists(int languageId){

        var result=this.languageDao.existsById(languageId);
        if (!result){
            return new ErrorResult(languageWordService.getByLanguageAndKeyId("Böyle bir dil yok",Integer.parseInt(environment.getProperty("language"))));

        }
        return new SuccessResult();
    }

    private Result checkIfLanguageNameExists(String languageName) {
        if (this.languageDao.existsLanguageByName(languageName)) {
            return new ErrorResult(languageWordService.getByLanguageAndKeyId(Messages.ExistColor,Integer.parseInt(environment.getProperty("language"))));

        }
        return new SuccessResult();
    }

}
