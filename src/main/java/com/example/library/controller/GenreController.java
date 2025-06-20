package com.example.library.controller;

import com.example.library.entity.Genre;
import com.example.library.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
@Tag(name = "Genre Controller", description = "APIs for managing genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @Operation(summary = "Create a new genre")
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(genreService.createGenre(genre), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing genre")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable Long id,
            @RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.updateGenre(id, genre));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a genre by ID")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }

    @GetMapping
    @Operation(summary = "Get all genres with pagination")
    public ResponseEntity<Page<Genre>> getAllGenres(Pageable pageable) {
        return ResponseEntity.ok(genreService.getAllGenres(pageable));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all genres")
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/search")
    @Operation(summary = "Search genres")
    public ResponseEntity<Page<Genre>> searchGenres(
            @RequestParam String searchTerm,
            Pageable pageable) {
        return ResponseEntity.ok(genreService.searchGenres(searchTerm, pageable));
    }
} 