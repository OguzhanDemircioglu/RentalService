package com.etiya.RentACarSpringProject.entities.complexTypies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private int id;

    private String translation;

    private int languageId;

    private int wordId;

    private String name;

    private String key;
}
