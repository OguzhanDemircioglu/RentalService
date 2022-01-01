package com.etiya.RentACarSpringProject.ws.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageWordService;
import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageWordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("api/languageWord")
public class LanguageWordsController {
    private LanguageWordService languageWordService;
    @Autowired
    public LanguageWordsController(LanguageWordService languageWordService){
        this.languageWordService = languageWordService;
    }

    @GetMapping("all")
    public DataResult<List<LanguageWordDto>> getAll(){ return languageWordService.getAll();}

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateLanguageWordRequest createLanguageWordRequest){
        return this.languageWordService.save(createLanguageWordRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateLanguageWordRequest updateLanguageWordRequest){
        return this.languageWordService.update(updateLanguageWordRequest);
    }

    @DeleteMapping("delete")
    public  Result delete(@RequestBody @Valid DeleteLanguageWordRequest deleteLanguageWordRequest){
        return this.languageWordService.delete(deleteLanguageWordRequest);
    }







}
