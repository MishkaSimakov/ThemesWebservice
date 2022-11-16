package com.example.themes.repositories;

import com.example.themes.models.Theme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
    @Query("select s.id, s.title from Theme s")
    List<String> selectThemeTitles();
}
