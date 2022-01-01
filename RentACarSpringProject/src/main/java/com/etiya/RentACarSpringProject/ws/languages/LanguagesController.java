package com.etiya.RentACarSpringProject.ws.languages;


import com.etiya.RentACarSpringProject.business.abstracts.languages.LanguageService;
import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.CreateLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.DeleteLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.UpdateLanguageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/language")
public class LanguagesController {

    private LanguageService languageService;
    @Autowired
    public LanguagesController(LanguageService LanguageService){
        this.languageService = LanguageService;
    }

    @GetMapping("all")
    public DataResult<List<LanguageDto>> getAll(){ return languageService.getAll();}

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateLanguageRequest createLanguageRequest){
        return this.languageService.save(createLanguageRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateLanguageRequest updateLanguageRequest){
        return this.languageService.update(updateLanguageRequest);
    }

    @DeleteMapping("delete")
    public  Result delete(@RequestBody @Valid DeleteLanguageRequest deleteLanguageRequest){
        return this.languageService.delete(deleteLanguageRequest);
    }
}
