package com.example.library.service.impl;

import com.example.library.entity.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));

        existingAuthor.setName(author.getName());
        existingAuthor.setBiography(author.getBiography());

        return authorRepository.save(existingAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Author> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Author> searchAuthors(String searchTerm, Pageable pageable) {
        return authorRepository.searchAuthors(searchTerm, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return authorRepository.existsByName(name);
    }
} 