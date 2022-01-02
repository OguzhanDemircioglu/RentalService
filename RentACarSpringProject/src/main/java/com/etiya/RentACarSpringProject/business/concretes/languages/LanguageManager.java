package com.etiya.RentACarSpringProject.business.concretes.languages;


import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.CreateLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.DeleteLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.UpdateLanguageRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.ErrorResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
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
        Language language = modelMapperService.forRequest().map(createLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult();

    }

    @Override
    public Result update(@Valid UpdateLanguageRequest updateLanguageRequest) {
        Language language = modelMapperService.forRequest().map(updateLanguageRequest, Language.class);
        this.languageDao.save(language);
        return new SuccessResult();
    }

    @Override
    public Result delete(@Valid DeleteLanguageRequest deleteLanguageRequest) {

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
}
