package com.etiya.RentACarSpringProject.ws.languages;

import com.etiya.RentACarSpringProject.business.abstracts.languages.WordService;
import com.etiya.RentACarSpringProject.business.dtos.languages.WordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.CreateWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.DeleteWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Word.UpdateWordRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("api/word")
public class WordsController {

    private WordService wordService;
    @Autowired
    public WordsController(WordService WordService){
        this.wordService = WordService;
    }

    @GetMapping("all")
    public DataResult<List<WordDto>> getAll(){ return wordService.getAll();}

    @PostMapping("add")
    public Result add(@RequestBody @Valid CreateWordRequest createWordRequest){
        return this.wordService.save(createWordRequest);
    }

    @PutMapping("update")
    public Result update(@RequestBody @Valid UpdateWordRequest updateWordRequest){
        return this.wordService.update(updateWordRequest);
    }

    @DeleteMapping("delete")
    public  Result delete(@RequestBody @Valid DeleteWordRequest deleteWordRequest){
        return this.wordService.delete(deleteWordRequest);
    }
}
