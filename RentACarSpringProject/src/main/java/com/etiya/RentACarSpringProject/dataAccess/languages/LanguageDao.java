package com.etiya.RentACarSpringProject.dataAccess.languages;

import com.etiya.RentACarSpringProject.entities.languages.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageDao extends JpaRepository<Language, Integer> {
    boolean existsLanguageByName(String brandName);
}
