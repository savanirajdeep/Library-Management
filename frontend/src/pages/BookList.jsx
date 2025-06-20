import { useState, useEffect } from 'react';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { bookService } from '../services/api';
import { useAuth } from '../contexts/AuthContext';

const BookList = () => {
  const [page, setPage] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [debouncedSearchTerm, setDebouncedSearchTerm] = useState('');
  const [sortField, setSortField] = useState('title');
  const [sortDirection, setSortDirection] = useState('asc');
  const { isAdmin } = useAuth();
  const queryClient = useQueryClient();
  
  console.log('BookList render - isAdmin:', isAdmin()); // Debug log

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedSearchTerm(searchTerm);
      setPage(0); 
    }, 500); 

    return () => {
      clearTimeout(handler);
    };
  }, [searchTerm]);

  const { data, isLoading, error } = useQuery({
    queryKey: ['books', page, debouncedSearchTerm, sortField, sortDirection],
    queryFn: () => bookService.getAllBooks(page, 10, debouncedSearchTerm, sortField, sortDirection),
    placeholderData: (previousData) => previousData,
  });

  const handleSort = (field) => {
    if (field === sortField) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('asc');
    }
    queryClient.invalidateQueries(['books']);
  };

  if (isLoading && !data) return <div>Loading...</div>;
  if (error) return <div>Error loading books</div>;

  return (
    <div>
      <div className="row mb-4">
        <div className="col-md-6">
          <form onSubmit={(e) => e.preventDefault()} className="d-flex">
            <input
              type="text"
              className="form-control"
              placeholder="Search by title, author, or genre..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </form>
        </div>
        {isAdmin() && (
          <div className="col-md-6 text-end">
            <Link to="/books/new" className="btn btn-success">
              Add New Book
            </Link>
          </div>
        )}
      </div>

      <div className="table-responsive">
        <table className="table table-striped">
          <thead>
            <tr>
              <th onClick={() => handleSort('title')} style={{ cursor: 'pointer' }}>
                Title {sortField === 'title' && (sortDirection === 'asc' ? '↑' : '↓')}
              </th>
              <th onClick={() => handleSort('author')} style={{ cursor: 'pointer' }}>
                Author {sortField === 'author' && (sortDirection === 'asc' ? '↑' : '↓')}
              </th>
              <th onClick={() => handleSort('genre')} style={{ cursor: 'pointer' }}>
                Genre {sortField === 'genre' && (sortDirection === 'asc' ? '↑' : '↓')}
              </th>
              <th>Cover</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {data?.content.map((book) => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.authors.map(author => author.name).join(', ')}</td>
                <td>{book.genre.name}</td>
                <td>
                  {book.coverImagePath && (
                    <img
                      src={`http://localhost:8080/uploads/covers/${book.coverImagePath}`}
                      alt={book.title}
                      style={{ width: '50px', height: '75px', objectFit: 'cover' }}
                    />
                  )}
                </td>
                <td>
                  {isAdmin() && (
                    <>
                      <Link to={`/books/edit/${book.id}`} className="btn btn-sm btn-primary me-2">
                        Edit
                      </Link>
                      <button
                        className="btn btn-sm btn-danger"
                        onClick={() => {
                          if (window.confirm('Are you sure you want to delete this book?')) {
                            bookService.deleteBook(book.id);
                          }
                        }}
                      >
                        Delete
                      </button>
                    </>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <nav>
        <ul className="pagination justify-content-center">
          <li className={`page-item ${page === 0 ? 'disabled' : ''}`}>
            <button className="page-link" onClick={() => setPage(page - 1)}>
              Previous
            </button>
          </li>
          <li className="page-item">
            <span className="page-link">Page {page + 1}</span>
          </li>
          <li className={`page-item ${data?.last ? 'disabled' : ''}`}>
            <button className="page-link" onClick={() => setPage(page + 1)}>
              Next
            </button>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default BookList; 