package com.etiya.RentACarSpringProject.business.abstracts.languages;

import com.etiya.RentACarSpringProject.business.dtos.languages.LanguageDto;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.CreateLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.DeleteLanguageRequest;
import com.etiya.RentACarSpringProject.business.requests.languages.Language.UpdateLanguageRequest;
import com.etiya.RentACarSpringProject.core.results.DataResult;
import com.etiya.RentACarSpringProject.core.results.Result;

import javax.validation.Valid;
import java.util.List;

public interface LanguageService {

    DataResult<List<LanguageDto>> getAll();
    Result save( CreateLanguageRequest createLanguageRequest);
    Result update( UpdateLanguageRequest updateLanguageRequest);
    Result delete( DeleteLanguageRequest deleteLanguageRequest);
}

