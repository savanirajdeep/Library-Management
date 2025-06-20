package com.example.library.service.impl;

import com.example.library.entity.Genre;
import com.example.library.repository.GenreRepository;
import com.example.library.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long id, Genre genre) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));

        existingGenre.setName(genre.getName());
        existingGenre.setDescription(genre.getDescription());

        return genreRepository.save(existingGenre);
    }

    @Override
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
        genreRepository.delete(genre);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Genre> getAllGenres(Pageable pageable) {
        return genreRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Genre> searchGenres(String searchTerm, Pageable pageable) {
        return genreRepository.searchGenres(searchTerm, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return genreRepository.existsByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }
} 