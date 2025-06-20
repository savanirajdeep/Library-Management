package com.example.library.service.impl;

import com.example.library.entity.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final Path fileStorageLocation = Paths.get("uploads/covers").toAbsolutePath().normalize();

    @Override
    public Book createBook(Book book, MultipartFile coverImage) {
        if (coverImage != null && !coverImage.isEmpty()) {
            String fileName = saveCoverImage(coverImage);
            book.setCoverImagePath(fileName);
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book, MultipartFile coverImage) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

        if (coverImage != null && !coverImage.isEmpty()) {
            String fileName = saveCoverImage(coverImage);
            book.setCoverImagePath(fileName);
        }

        existingBook.setTitle(book.getTitle());
        existingBook.setDescription(book.getDescription());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPublishDate(book.getPublishDate());
        existingBook.setGenre(book.getGenre());
        existingBook.setAuthors(book.getAuthors());
        if (book.getCoverImagePath() != null) {
            existingBook.setCoverImagePath(book.getCoverImagePath());
        }

        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        
        if (book.getCoverImagePath() != null) {
            try {
                Files.deleteIfExists(fileStorageLocation.resolve(book.getCoverImagePath()));
            } catch (IOException e) {
                // Log the error but continue with book deletion
                e.printStackTrace();
            }
        }
        
        bookRepository.delete(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(String searchTerm, Pageable pageable) {
        if (searchTerm != null && !searchTerm.isEmpty()) {
            return bookRepository.searchBooks(searchTerm, pageable);
        }
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findByGenre(Long genreId, Pageable pageable) {
        return bookRepository.findByGenreId(genreId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findByAuthor(Long authorId, Pageable pageable) {
        return bookRepository.findByAuthorId(authorId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByIsbn(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    private String saveCoverImage(MultipartFile file) {
        try {
            Files.createDirectories(fileStorageLocation);
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }
} 