import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { bookService, genreService } from '../services/api';

const BookForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const isEditing = Boolean(id);

  const [bookData, setBookData] = useState({
    title: '',
    author: '',
    genre: '',
    description: '',
    isbn: '',
    publishedYear: new Date().getFullYear(),
  });
  const [coverImage, setCoverImage] = useState(null);
  const [error, setError] = useState('');
  const [showNewGenreInput, setShowNewGenreInput] = useState(false);
  const [newGenreName, setNewGenreName] = useState('');

  const { data: existingBook } = useQuery({
    queryKey: ['book', id],
    queryFn: () => bookService.getBookById(id),
    enabled: isEditing,
  });

  const { data: genres = [] } = useQuery({
    queryKey: ['genres'],
    queryFn: () => genreService.getAllGenres(),
  });

  useEffect(() => {
    if (existingBook) {
      setBookData({
        title: existingBook.title || '',
        author: existingBook.authors && existingBook.authors.length > 0 
          ? existingBook.authors.map(author => author.name).join(', ') 
          : '',
        genre: existingBook.genre ? existingBook.genre.name : '',
        description: existingBook.description || '',
        isbn: existingBook.isbn || '',
        publishedYear: existingBook.publishDate 
          ? new Date(existingBook.publishDate).getFullYear() 
          : new Date().getFullYear(),
      });
    }
  }, [existingBook]);

  const createMutation = useMutation({
    mutationFn: (data) => bookService.createBook(data, coverImage),
    onSuccess: () => {
      queryClient.invalidateQueries(['books']);
      navigate('/');
    },
    onError: (error) => {
      setError('Failed to create book. Please try again.');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data) => bookService.updateBook(id, data, coverImage),
    onSuccess: () => {
      queryClient.invalidateQueries(['books']);
      navigate('/');
    },
    onError: (error) => {
      setError('Failed to update book. Please try again.');
    },
  });

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const dataToSend = { ...bookData };
    if (showNewGenreInput) {
      if (!newGenreName.trim()) {
        setError('Please enter a name for the new genre.');
        return;
      }
      dataToSend.genre = newGenreName;
    } else if (!dataToSend.genre) {
      setError('Please select a genre.');
      return;
    }
    setError(''); // Clear previous errors

    if (isEditing) {
      updateMutation.mutate(dataToSend);
    } else {
      createMutation.mutate(dataToSend);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBookData((prev) => ({
      ...prev,
      [name]: value,
    }));

    // If genre is changed to "new", show the new genre input
    if (name === 'genre' && value === 'new') {
      setShowNewGenreInput(true);
      setNewGenreName('');
    } else if (name === 'genre' && value !== 'new') {
      setShowNewGenreInput(false);
    }
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      setCoverImage(file);
    }
  };

  return (
    <div className="row justify-content-center">
      <div className="col-md-8">
        <div className="card">
          <div className="card-body">
            <h2 className="card-title text-center mb-4">
              {isEditing ? 'Edit Book' : 'Add New Book'}
            </h2>
            {error && <div className="alert alert-danger">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="title" className="form-label">
                  Title
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="title"
                  name="title"
                  value={bookData.title}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="author" className="form-label">
                  Author
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="author"
                  name="author"
                  value={bookData.author}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="genre" className="form-label">
                  Genre
                </label>
                <select
                  className="form-control"
                  id="genre"
                  name="genre"
                  value={bookData.genre}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select a genre</option>
                  {genres.map((genre) => (
                    <option key={genre.id} value={genre.name}>
                      {genre.name}
                    </option>
                  ))}
                  <option value="new">+ Add New Genre</option>
                </select>
                {showNewGenreInput && (
                  <div className="mt-2">
                    <input
                      type="text"
                      className="form-control"
                      placeholder="Enter new genre name"
                      value={newGenreName}
                      onChange={(e) => {
                        setNewGenreName(e.target.value);
                      }}
                    />
                  </div>
                )}
              </div>
              <div className="mb-3">
                <label htmlFor="description" className="form-label">
                  Description
                </label>
                <textarea
                  className="form-control"
                  id="description"
                  name="description"
                  value={bookData.description}
                  onChange={handleChange}
                  rows="3"
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="isbn" className="form-label">
                  ISBN
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="isbn"
                  name="isbn"
                  value={bookData.isbn}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="publishedYear" className="form-label">
                  Published Year
                </label>
                <input
                  type="number"
                  className="form-control"
                  id="publishedYear"
                  name="publishedYear"
                  value={bookData.publishedYear}
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="mb-3">
                <label htmlFor="coverImage" className="form-label">
                  Cover Image
                </label>
                <input
                  type="file"
                  className="form-control"
                  id="coverImage"
                  accept="image/*"
                  onChange={handleImageChange}
                />
              </div>
              <div className="d-grid gap-2">
                <button type="submit" className="btn btn-primary">
                  {isEditing ? 'Update Book' : 'Add Book'}
                </button>
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => navigate('/')}
                >
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookForm; 