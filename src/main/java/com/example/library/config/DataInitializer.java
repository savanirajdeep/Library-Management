package com.example.library.config;

import com.example.library.entity.Role;
import com.example.library.entity.User;
import com.example.library.entity.Author;
import com.example.library.entity.Genre;
import com.example.library.entity.Book;
import com.example.library.repository.RoleRepository;
import com.example.library.repository.UserRepository;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.GenreRepository;
import com.example.library.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        // Create default roles if they don't exist
        if (!roleRepository.existsByName("ROLE_USER")) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        // Create admin user if not exists
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN").get());
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        // Create sample genres if they don't exist
        Genre fiction = createGenreIfNotExists("Fiction", "Fictional literature and stories");
        Genre mystery = createGenreIfNotExists("Mystery", "Mystery and detective fiction");
        Genre scifi = createGenreIfNotExists("Science Fiction", "Science fiction and fantasy");
        Genre romance = createGenreIfNotExists("Romance", "Romance novels and love stories");
        Genre historicalFiction = createGenreIfNotExists("Historical Fiction", "Fiction set in a particular historical period");
        Genre thriller = createGenreIfNotExists("Thriller", "Suspenseful fiction with fast-paced plots");
        Genre horror = createGenreIfNotExists("Horror", "Fiction designed to evoke fear");
        Genre fantasy = createGenreIfNotExists("Fantasy", "Fantasy and magical fiction");
        

        // Create sample authors if they don't exist
        Author author1 = createAuthorIfNotExists("J.K. Rowling", "British author best known for the Harry Potter series");
        Author author2 = createAuthorIfNotExists("Agatha Christie", "English writer known for her detective novels");
        Author author3 = createAuthorIfNotExists("Isaac Asimov", "American writer and professor of biochemistry");
        Author author4 = createAuthorIfNotExists("Jane Austen", "English novelist known for romantic fiction");
        Author author5 = createAuthorIfNotExists("J.R.R. Tolkien", "English writer and philologist");
        Author author6 = createAuthorIfNotExists("George Orwell", "English novelist, essayist, journalist and critic");
        Author author7 = createAuthorIfNotExists("F. Scott Fitzgerald", "American novelist and short story writer");
        Author author8 = createAuthorIfNotExists("Herman Melville", "American novelist, short story writer, and poet");
        Author author9 = createAuthorIfNotExists("Leo Tolstoy", "Russian writer known for War and Peace");
        Author author10 = createAuthorIfNotExists("Stephen King", "American author of horror, supernatural fiction, and fantasy");
        Author author11 = createAuthorIfNotExists("Charles Dickens", "English novelist and social critic");
        Author author12 = createAuthorIfNotExists("Mark Twain", "American writer and humorist best known for The Adventures of Huckleberry Finn");
        Author author13 = createAuthorIfNotExists("Mary Shelley", "English novelist known for writing Frankenstein");
        Author author14 = createAuthorIfNotExists("Kurt Vonnegut", "American author known for his satirical novels");
        Author author15 = createAuthorIfNotExists("Haruki Murakami", "Japanese writer known for surreal novels blending reality and fantasy");
        Author author16 = createAuthorIfNotExists("George R.R. Martin", "American novelist known for A Song of Ice and Fire series");


        // Create sample books if they don't exist
        createBookIfNotExists("Harry Potter and the Philosopher's Stone", 
            "The first novel in the Harry Potter series", 
            "9780747532699", 
            fiction, 
            Set.of(author1),
            "harry_potter.jpg");

        createBookIfNotExists("Murder on the Orient Express", 
            "A detective novel featuring Hercule Poirot", 
            "9780062073495", 
            mystery, 
            Set.of(author2),
            "murder_on_the_orient_express.jpg");

        createBookIfNotExists("Foundation", 
            "The first novel in the Foundation series", 
            "9780553293357", 
            scifi, 
            Set.of(author3),
            "foundation.jpg");

        createBookIfNotExists("Pride and Prejudice", 
            "A romantic novel of manners", 
            "9780141439518", 
            romance, 
            Set.of(author4),
            "pride_and_prejudice.jpg");

        createBookIfNotExists("The Hobbit", 
            "A fantasy novel about Bilbo Baggins", 
            "9780547928241", 
            fiction, 
            Set.of(author5),
            "the_hobbit.jpg");

        createBookIfNotExists("1984",
            "A dystopian social science fiction novel and cautionary tale",
            "9780451524935",
            scifi,
            Set.of(author6),
            "1984.jpg");

        createBookIfNotExists("The Great Gatsby",
            "A novel about the American dream",
            "9780743273565",
            fiction,
            Set.of(author7),
            "the_great_gatsby.jpg");

        createBookIfNotExists("Moby Dick",
            "A novel about the voyage of the whaling ship Pequod",
            "9781503280786",
            fiction,
            Set.of(author8),
            "moby_dick.jpg");

        createBookIfNotExists("Animal Farm",
            "A satirical allegorical novella",
            "9780451526342",
            scifi,
            Set.of(author6),
            "animal_farm.jpg");

        createBookIfNotExists("To Kill a Mockingbird",
            "A novel about the seriousness of racism",
            "9780061120084",
            fiction,
            Set.of(createAuthorIfNotExists("Harper Lee", "American novelist")),
            "to_kill_a_mockingbird.jpg");

        createBookIfNotExists("The Catcher in the Rye",
            "A novel about teenage angst and alienation",
            "9780316769488",
            fiction,
            Set.of(createAuthorIfNotExists("J.D. Salinger", "American writer")),
            "the_catcher_in_the_rye.jpg");
            
        createBookIfNotExists("Brave New World",
            "A dystopian novel",
            "9780060850524",
            scifi,
            Set.of(createAuthorIfNotExists("Aldous Huxley", "English writer and philosopher")),
            "brave_new_world.jpg");

        createBookIfNotExists("Lord of the Flies",
            "A novel about a group of British boys stranded on an uninhabited island",
            "9780399501487",
            fiction,
            Set.of(createAuthorIfNotExists("William Golding", "British novelist, playwright, and poet")),
            "lord_of_the_flies.jpg");

        createBookIfNotExists("Fahrenheit 451",
            "A dystopian novel about a future American society where books are outlawed",
            "9781451673319",
            scifi,
            Set.of(createAuthorIfNotExists("Ray Bradbury", "American author and screenwriter")),
            "fahrenheit_451.jpg");

        createBookIfNotExists("The Lord of the Rings",
            "An epic high-fantasy novel",
            "9780618640157",
            fiction,
            Set.of(author5),    
            "the_lord_of_the_rings.jpg");
        
            createBookIfNotExists("War and Peace", 
            "An epic historical novel about Russian society during the Napoleonic wars", 
            "9780140447934", 
            historicalFiction, 
            Set.of(author9),
            "war_and_peace.jpg");
        
        createBookIfNotExists("The Shining", 
            "A horror novel about a family staying at an isolated hotel", 
            "9780307743657", 
            horror, 
            Set.of(author10),
            "the_shining.jpg");
        
        createBookIfNotExists("A Tale of Two Cities", 
            "A novel set during the French Revolution and the Reign of Terror", 
            "9781853260391", 
            historicalFiction, 
            Set.of(author11),
            "a_tale_of_two_cities.jpg");
        
        createBookIfNotExists("The Adventures of Huckleberry Finn", 
            "A story about the adventures of a boy and a runaway slave on the Mississippi River", 
            "9780142437179", 
            thriller, 
            Set.of(author12),
            "huckleberry_finn.jpg");
        
        createBookIfNotExists("Frankenstein", 
            "A novel about a scientist who creates a monstrous creature", 
            "9780141439471", 
            horror, 
            Set.of(author13),
            "frankenstein.jpg");
        
        createBookIfNotExists("Slaughterhouse-Five", 
            "A novel about the bombing of Dresden in WWII and time travel", 
            "9780440180296", 
            scifi, 
            Set.of(author14),
            "slaughterhouse_five.jpg");
        
        createBookIfNotExists("Norwegian Wood", 
            "A coming-of-age novel exploring love, loss, and mental illness", 
            "9780375704024", 
            romance, 
            Set.of(author15),
            "norwegian_wood.jpg");
        
        createBookIfNotExists("A Game of Thrones", 
            "The first book in the A Song of Ice and Fire series, filled with political intrigue", 
            "9780553593716", 
            fantasy, 
            Set.of(author16),
            "game_of_thrones.jpg");
        
    }

    private Genre createGenreIfNotExists(String name, String description) {
        return genreRepository.findByName(name)
            .orElseGet(() -> {
                Genre genre = new Genre();
                genre.setName(name);
                genre.setDescription(description);
                return genreRepository.save(genre);
            });
    }

    private Author createAuthorIfNotExists(String name, String description) {
        return authorRepository.findByName(name)
            .orElseGet(() -> {
                Author author = new Author();
                author.setName(name);
                author.setDescription(description);
                return authorRepository.save(author);
            });
    }

    private void createBookIfNotExists(String title, String description, String isbn, Genre genre, Set<Author> authors, String coverImage) {
        if (!bookRepository.existsByIsbn(isbn)) {
            Book book = new Book();
            book.setTitle(title);
            book.setDescription(description);
            book.setIsbn(isbn);
            book.setGenre(genre);
            book.setAuthors(authors);
            book.setPublishDate(LocalDateTime.now());
            book.setCoverImagePath(coverImage);
            bookRepository.save(book);
        }
    }
} 