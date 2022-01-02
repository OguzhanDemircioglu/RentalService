package com.etiya.RentACarSpringProject.dataAccess.languages;


import com.etiya.RentACarSpringProject.entities.languages.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordDao extends JpaRepository<Word, Integer> {
	
	Word getByKey(String key);
}
