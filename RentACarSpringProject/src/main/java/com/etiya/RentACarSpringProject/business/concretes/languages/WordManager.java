package com.etiya.RentACarSpringProject.business.concretes.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.abstracts.languages.WordService;
import com.etiya.RentACarSpringProject.business.constants.Messages;
import com.etiya.RentACarSpringProject.business.dtos.languages.WordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.CreateWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.DeleteWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.UpdateWordRequest;
import com.etiya.RentACarSpringProject.core.business.BusinessRules;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.*;
import com.etiya.RentACarSpringProject.dataAccess.languages.WordDao;
import com.etiya.RentACarSpringProject.entities.languages.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordManager implements WordService {

    private WordDao wordDao;
    private ModelMapperService modelMapperService;
    private Environment environment;
    private LanguageWordService languageWordService;
    
    @Autowired
    public WordManager(WordDao wordDao,ModelMapperService modelMapperService, Environment environment,
            @Lazy LanguageWordService languageWordService) {
        
    	this.wordDao = wordDao;
        this.modelMapperService=modelMapperService;
        this.environment = environment;
        this.languageWordService = languageWordService;
    }

    @Override
    public DataResult<List<WordDto>> getAll() {
        List<Word> result = this.wordDao.findAll();
        List<WordDto> response = result.stream()
                .map(word -> modelMapperService.forDto().map(word, WordDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<WordDto>>(response);
    }

    @Override
    public Result save(@Valid CreateWordRequest createWordRequest) {
        Word word = modelMapperService.forRequest().map(createWordRequest, Word.class);
        this.wordDao.save(word);
        return new SuccessResult();
    }

    @Override
    public Result update(@Valid UpdateWordRequest updateWordRequest) {

        var result = BusinessRules.run(checkIfLanguageWordIdExists(updateWordRequest.getWordId()));

        if (result != null) {
            return result;
        }

        Word word = modelMapperService.forRequest().map(updateWordRequest, Word.class);
        this.wordDao.save(word);
        return new SuccessResult();
    }

    @Override
    public Result delete(@Valid DeleteWordRequest deleteWordRequest) {

        var result = BusinessRules.run(checkIfLanguageWordIdExists(deleteWordRequest.getWordId()));

        if (result != null) {
            return result;
        }

        this.wordDao.deleteById(deleteWordRequest.getWordId());
        return new SuccessResult();
    }
    @Override
    public Result checkWordIdExists(int wordId) {
        if (this.wordDao.existsById(wordId)) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }

    @Override
    public Result checkKeyExists(String key) {
        Word word = this.wordDao.getByKey(key);
        if (word != null) {
            return new SuccessResult();
        }
        return new ErrorResult();
    }

    private Result ifKeyDuplicated(String key){
        Word word = this.wordDao.getByKey(key);
        if (word != null){
            return new ErrorResult("Bu kelimeden mevcut");
        }
        return new SuccessResult();
    }


    public Result  checkIfLanguageWordIdExists(int wordId){

        var result=this.wordDao.existsById(wordId);
        if (!result){
            return new ErrorResult(languageWordService.getByLanguageAndKeyId("Böyle bir kelime yok",Integer.parseInt(environment.getProperty("language"))));

        }
        return new SuccessResult();
    }


}
