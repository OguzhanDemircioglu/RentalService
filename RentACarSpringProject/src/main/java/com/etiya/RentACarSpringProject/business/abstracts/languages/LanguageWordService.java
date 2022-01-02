package com.etiya.RentACarSpringProject.business.abstracts.languages;


import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageWordDto;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.CreateLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.DeleteLanguageWordRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.LanguageWord.UpdateLanguageWordRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

import javax.validation.Valid;
import java.util.List;

public interface LanguageWordService {

    DataResult<List<LanguageWordDto>> getAll();
    Result save( CreateLanguageWordRequest createLanguageRequest);
    Result update( UpdateLanguageWordRequest updateLanguageRequest);
    Result delete( DeleteLanguageWordRequest deleteLanguageRequest);

    String getByLanguageAndKeyId(String key, int language);
}

