package com.etiya.RentACarSpringProject.dataAccess.languages;

import com.etiya.RentACarSpringProject.entities.languages.LanguageWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LanguageWordDao extends JpaRepository<LanguageWord, Integer> {

    @Query(value = "select lw.translation from languages l inner join languagesword lw on l.language_id=lw.language_id\n" +
            "inner join words w on w.word_id=lw.word_id where w.word_id=:wordId and l.language_id=:languageId", nativeQuery = true)
    String getMessageByLanguageIdAndKey(int wordId,int languageId);
}
