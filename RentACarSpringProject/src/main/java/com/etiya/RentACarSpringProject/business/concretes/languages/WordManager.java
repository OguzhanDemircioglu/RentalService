package com.etiya.RentACarSpringProject.business.concretes.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.WordService;
import com.etiya.RentACarSpringProject.business.dtos.languages.WordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.CreateWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.DeleteWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.UpdateWordRequest;
import com.etiya.RentACarSpringProject.core.mapping.ModelMapperService;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import com.etiya.RentACarSpringProject.core.results.SuccessDataResult;
import com.etiya.RentACarSpringProject.core.results.SuccessResult;
import com.etiya.RentACarSpringProject.dataAccess.languages.WordDao;
import com.etiya.RentACarSpringProject.entities.languages.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordManager implements WordService {

    private WordDao wordDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public WordManager(WordDao wordDao,ModelMapperService modelMapperService) {
        this.wordDao = wordDao;
        this.modelMapperService=modelMapperService;
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
        Word word = modelMapperService.forRequest().map(updateWordRequest, Word.class);
        this.wordDao.save(word);
        return new SuccessResult();
    }

    @Override
    public Result delete(@Valid DeleteWordRequest deleteWordRequest) {
        this.wordDao.deleteById(deleteWordRequest.getWordId());
        return new SuccessResult();
    }
}
