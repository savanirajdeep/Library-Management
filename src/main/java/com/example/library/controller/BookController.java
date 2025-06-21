package com.example.library.controller;

import com.example.library.entity.Book;
import com.example.library.entity.Author;
import com.example.library.entity.Genre;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.GenreRepository;
import com.example.library.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "APIs for managing books")
public class BookController {

    private final BookService bookService;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @PostMapping
    @Operation(summary = "Create a new book")
    public ResponseEntity<Book> createBook(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("author") String authorName,
            @RequestParam("genre") String genreName,
            @RequestParam(value = "publishedYear", required = false) Integer publishedYear,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage) {
        
        // Create or find author
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    newAuthor.setDescription("Author biography");
                    return authorRepository.save(newAuthor);
                });

        // Create or find genre
        Genre genre = genreRepository.findByName(genreName)
                .orElseGet(() -> {
                    Genre newGenre = new Genre();
                    newGenre.setName(genreName);
                    newGenre.setDescription("Genre description");
                    return genreRepository.save(newGenre);
                });

        // Create book
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setAuthors(Set.of(author));
        
        if (publishedYear != null) {
            book.setPublishDate(LocalDateTime.of(publishedYear, 1, 1, 0, 0));
        }

        return new ResponseEntity<>(bookService.createBook(book, coverImage), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing book")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("author") String authorName,
            @RequestParam("genre") String genreName,
            @RequestParam(value = "publishedYear", required = false) Integer publishedYear,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage) {
        
        // Create or find author
        Author author = authorRepository.findByName(authorName)
                .orElseGet(() -> {
                    Author newAuthor = new Author();
                    newAuthor.setName(authorName);
                    newAuthor.setDescription("Author Description");
                    return authorRepository.save(newAuthor);
                });

        // Create or find genre
        Genre genre = genreRepository.findByName(genreName)
                .orElseGet(() -> {
                    Genre newGenre = new Genre();
                    newGenre.setName(genreName);
                    newGenre.setDescription("Genre description");
                    return genreRepository.save(newGenre);
                });

        // Create book object for update
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setAuthors(Set.of(author));
        
        if (publishedYear != null) {
            book.setPublishDate(LocalDateTime.of(publishedYear, 1, 1, 0, 0));
        }

        return ResponseEntity.ok(bookService.updateBook(id, book, coverImage));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book by ID")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    @Operation(summary = "Get all books with pagination and search")
    public ResponseEntity<Page<Book>> getAllBooks(
            @RequestParam(required = false) String searchTerm,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(searchTerm, pageable));
    }

    @GetMapping("/genre/{genreId}")
    @Operation(summary = "Get books by genre")
    public ResponseEntity<Page<Book>> getBooksByGenre(
            @PathVariable Long genreId,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.findByGenre(genreId, pageable));
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Get books by author")
    public ResponseEntity<Page<Book>> getBooksByAuthor(
            @PathVariable Long authorId,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.findByAuthor(authorId, pageable));
    }

    @GetMapping("/title")
    @Operation(summary = "Get books by title")
    public ResponseEntity<Page<Book>> getBooksByTitle(
            @RequestParam String title,
            Pageable pageable) {
        return ResponseEntity.ok(bookService.findByTitle(title, pageable));
    }
} 