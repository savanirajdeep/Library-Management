# Library Management System

A comprehensive Library Management System built with Spring Boot and MySQL, featuring a React frontend.

## Features

- Book management (CRUD operations)
- Author management
- Genre management
- File upload for book cover images
- Pagination and filtering
- Search functionality
- Swagger/OpenAPI documentation
- JWT-based authentication
- Role-based access control (Admin/User roles)

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Node.js 16 or higher (for frontend)

## Database Configuration

The application uses MySQL as the database. Update the following properties in `src/main/resources/application.properties` according to your MySQL configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_management?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application

### Backend (Spring Boot)

1. Clone the repository
2. Configure the database properties
3. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Access the Swagger documentation at: http://localhost:8080/swagger-ui.html

### Frontend (React)

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Access the application at: http://localhost:5173

## User Credentials

### Default Admin User

The system automatically creates an admin user with the following credentials:

- **Email:** `admin@example.com`
- **Password:** `admin123`
- **Role:** `ROLE_ADMIN`

### Test User Registration

You can create additional test users by registering through the application:

1. Navigate to the registration page
2. Fill in the required information:
   - Name
   - Email
   - Password
3. New users will be assigned the `ROLE_USER` role by default

### Sample Test Users

For testing purposes, you can use these sample credentials (you'll need to register them first):

**Regular User:**
- **Email:** `savanirajdeep5@gmail.co/`
- **Password:** `admin123`
- **Role:** `ROLE_USER`

**Another Test User:**
- **Email:** `test@example.com`
- **Password:** `test123`
- **Role:** `ROLE_USER`

## API Endpoints

### Authentication

- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration

### Books

- `POST /api/books` - Create a new book (Admin only)
- `PUT /api/books/{id}` - Update a book (Admin only)
- `DELETE /api/books/{id}` - Delete a book (Admin only)
- `GET /api/books/{id}` - Get a book by ID
- `GET /api/books` - Get all books (paginated)
- `GET /api/books/search` - Search books
- `GET /api/books/genre/{genreId}` - Get books by genre
- `GET /api/books/author/{authorId}` - Get books by author
- `GET /api/books/title` - Get books by title

### Authors

- `POST /api/authors` - Create a new author (Admin only)
- `PUT /api/authors/{id}` - Update an author (Admin only)
- `DELETE /api/authors/{id}` - Delete an author (Admin only)
- `GET /api/authors/{id}` - Get an author by ID
- `GET /api/authors` - Get all authors

### Genres

- `POST /api/genres` - Create a new genre (Admin only)
- `PUT /api/genres/{id}` - Update a genre (Admin only)
- `DELETE /api/genres/{id}` - Delete a genre (Admin only)
- `GET /api/genres/{id}` - Get a genre by ID
- `GET /api/genres` - Get all genres

## File Upload

The system supports file uploads for book cover images. Files are stored in the `uploads/covers` directory and are accessible via the `/uploads/**` endpoint.

## Sample Data

The application comes pre-loaded with sample data including:

### Genres
- Fiction
- Mystery
- Science Fiction
- Romance

### Authors
- J.K. Rowling
- Agatha Christie
- Isaac Asimov
- Jane Austen
- J.R.R. Tolkien
- George Orwell
- F. Scott Fitzgerald
- Herman Melville
- Harper Lee
- J.D. Salinger
- Aldous Huxley
- William Golding
- Ray Bradbury

### Books
- Harry Potter and the Philosopher's Stone
- Murder on the Orient Express
- Foundation
- Pride and Prejudice
- The Hobbit
- 1984
- The Great Gatsby
- Moby Dick
- Animal Farm
- To Kill a Mockingbird
- The Catcher in the Rye
- Brave New World
- Lord of the Flies
- Fahrenheit 451
- The Lord of the Rings

## Development

### Project Structure

```
src/main/java/com/example/Library/
├── config/         # Configuration classes
├── controller/     # REST controllers
├── entity/         # JPA entities
├── exception/      # Exception handlers
├── repository/     # JPA repositories
├── security/       # JWT and security configuration
└── service/        # Service interfaces and implementations

frontend/
├── src/
│   ├── components/ # React components
│   ├── contexts/   # React contexts
│   ├── pages/      # Page components
│   └── services/   # API services
```

### Adding New Features

1. Create the entity class in the `entity` package
2. Create the repository interface in the `repository` package
3. Create the service interface and implementation in the `service` package
4. Create the controller in the `controller` package
5. Add any necessary configuration in the `config` package
6. Update the frontend components as needed

## Security

- JWT-based authentication
- Role-based access control
- Password encryption using BCrypt
- CORS configuration for frontend integration
- File upload security

## Testing

### Backend Testing
- Run tests with: `mvn test`
- Integration tests are included in the test package

### Frontend Testing
- Run tests with: `npm test` (in frontend directory)
- Component testing with React Testing Library

## Troubleshooting

### Common Issues

1. **Database Connection Error**: Ensure MySQL is running and credentials are correct
2. **Port Already in Use**: Change the port in `application.properties` or kill the process using the port
3. **Frontend Build Issues**: Clear node_modules and reinstall dependencies
4. **File Upload Issues**: Ensure the `uploads/covers` directory exists and has write permissions

### Logs

- Backend logs are available in the console when running with Maven
- Frontend logs are available in the browser console

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details. 