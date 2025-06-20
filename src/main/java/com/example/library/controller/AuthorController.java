package com.example.library.controller;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Author Controller", description = "APIs for managing authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @Operation(summary = "Create a new author")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorService.createAuthor(author), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing author")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @RequestBody Author author) {
        return ResponseEntity.ok(authorService.updateAuthor(id, author));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an author")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an author by ID")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @GetMapping
    @Operation(summary = "Get all authors with pagination")
    public ResponseEntity<Page<Author>> getAllAuthors(Pageable pageable) {
        return ResponseEntity.ok(authorService.getAllAuthors(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search authors")
    public ResponseEntity<Page<Author>> searchAuthors(
            @RequestParam String searchTerm,
            Pageable pageable) {
        return ResponseEntity.ok(authorService.searchAuthors(searchTerm, pageable));
    }
} 