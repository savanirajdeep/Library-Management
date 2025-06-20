package com.example.library.service;

import com.example.library.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Author createAuthor(Author author);
    
    Author updateAuthor(Long id, Author author);
    
    void deleteAuthor(Long id);
    
    Author getAuthorById(Long id);
    
    Page<Author> getAllAuthors(Pageable pageable);
    
    Page<Author> searchAuthors(String searchTerm, Pageable pageable);
    
    boolean existsByName(String name);
} 