package com.example.library.service;

import com.example.library.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenreService {
    Genre createGenre(Genre genre);
    
    Genre updateGenre(Long id, Genre genre);
    
    void deleteGenre(Long id);
    
    Genre getGenreById(Long id);
    
    Page<Genre> getAllGenres(Pageable pageable);
    
    Page<Genre> searchGenres(String searchTerm, Pageable pageable);
    
    boolean existsByName(String name);

    List<Genre> findAll();
} 