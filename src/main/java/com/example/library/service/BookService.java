package com.example.library.service;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Book createBook(Book book, MultipartFile coverImage);
    
    Book updateBook(Long id, Book book, MultipartFile coverImage);
    
    void deleteBook(Long id);
    
    Book getBookById(Long id);
    
    Page<Book> getAllBooks(String searchTerm, Pageable pageable);
    
    Page<Book> findByGenre(Long genreId, Pageable pageable);
    
    Page<Book> findByAuthor(Long authorId, Pageable pageable);
    
    Page<Book> findByTitle(String title, Pageable pageable);
    
    boolean existsByIsbn(String isbn);
} 