package com.etiya.RentACarSpringProject.business.dtos.languages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageWordDto {

    private int id;
    private int wordId;
    private int languageId;
    private String translation;
}
