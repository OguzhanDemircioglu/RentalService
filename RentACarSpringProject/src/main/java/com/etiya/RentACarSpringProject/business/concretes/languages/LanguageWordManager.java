package com.etiya.RentACarSpringProject.business.concretes.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.WordService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageWordManager implements LanguageWordService {
	
	  	@Value("${language}")
	    private Integer languageId; 
	  	@Value("${defaultLanguage}")
	    private Integer defaultLanguageId;
	
	    
    private LanguageWordDao languageWordDao;
    private ModelMapperService modelMapperService;
    private WordService wordService;
    private Environment environment;
    private LanguageService languageService;

    @Autowired
    public LanguageWordManager(LanguageWordDao languageWordDao, ModelMapperService modelMapperService, WordService wordService, Environment environment, LanguageService languageService) {
    	
        this.languageWordDao = languageWordDao;
        this.modelMapperService=modelMapperService;
        this.wordService=wordService;
        this.environment=environment;
        this.languageService=languageService;
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
    public Result save(CreateLanguageWordRequest createLanguageRequest) {

        LanguageWord languageWord = modelMapperService.forRequest()
            .map(createLanguageRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
    return new SuccessResult();
    }

    @Override
    public Result update(UpdateLanguageWordRequest updateLanguageRequest) {

        LanguageWord languageWord = modelMapperService.forRequest()
                .map(updateLanguageRequest, LanguageWord.class);
        this.languageWordDao.save(languageWord);
        return new SuccessResult();
    }

    @Override
    public Result delete(DeleteLanguageWordRequest deleteLanguageRequest) {
        this.languageWordDao.deleteById(deleteLanguageRequest.getId());
        return new SuccessResult();
    }

    @Override
    public String getByLanguageAndKeyId(String key, int language) {
        //getDefaultLanguage();
        String messageContent=this.languageWordDao.getMessageByLanguageIdAndKey(key,this.languageId);
        if (!wordService.checkKeyExists(key).isSuccess()){
            return key;
        }
        if (messageContent!=null){
            return messageContent;
        }
        return this.languageWordDao.getMessageByLanguageIdAndKey(key, language);
    }

    /*private void getDefaultLanguage(){
        if(!this.languageService.checkLanguageExists(this.languageId).isSuccess()){
            this.languageId=this.defaultLanguageId;
        }
    }*/
}