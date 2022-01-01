package com.etiya.RentACarSpringProject.business.concretes.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageWordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
import com.etiya.RentACarSpringProject.dataAccess.languages.LanguageWordDao;
import com.etiya.RentACarSpringProject.entities.languages.LanguageWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageWordManager implements LanguageWordService {
    private LanguageWordDao languageWordDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public LanguageWordManager(LanguageWordDao languageWordDao, ModelMapperService modelMapperService) {
        this.languageWordDao = languageWordDao;
        this.modelMapperService=modelMapperService;
    }

    @Override
    public DataResult<List<LanguageWordDto>> getAll() {
        List<LanguageWord> result =this.languageWordDao.findAll();
            List<LanguageWordDto> response =result.stream()
                .map(languageWord -> modelMapperService.forDto()
                        .map(languageWord, LanguageWordDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<LanguageWordDto>>(response);
    }

    @Override
    public Result save(@Valid CreateLanguageWordRequest createLanguageRequest) {

        LanguageWord languageWord = modelMapperService.forRequest()
            .map(createLanguageRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
    return new SuccessResult();
    }

    @Override
    public Result update(@Valid UpdateLanguageWordRequest updateLanguageRequest) {

        LanguageWord languageWord = modelMapperService.forRequest()
                .map(updateLanguageRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult();
    }

    @Override
    public Result delete(@Valid DeleteLanguageWordRequest deleteLanguageRequest) {
        this.languageWordDao.deleteById(deleteLanguageRequest.getId());
        return new SuccessResult();
    }

    @Override
    public String getByLanguageAndKeyId(int wordId, int language) {
        return this.languageWordDao.getMessageByLanguageIdAndKey(wordId, language);
    }
}
