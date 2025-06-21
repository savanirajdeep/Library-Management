import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add token to requests if it exists
api.interceptors.request.use((config) => {
  const token = sessionStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const authService = {
  login: async (credentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },
  register: async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },
  logout: () => {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
  },
};

export const genreService = {
  getAllGenres: async () => {
    const response = await api.get('/genres/all');
    return response.data;
  },
};

export const bookService = {
  getAllBooks: async (page = 0, size = 10, searchTerm = '', sortField = 'title', sortDirection = 'asc') => {
    const params = new URLSearchParams({
      page,
      size,
      sort: `${sortField},${sortDirection}`,
    });

    if (searchTerm) {
      params.append('searchTerm', searchTerm);
    }

    const response = await api.get(`/books`, { params });
    return response.data;
  },
  getBookById: async (id) => {
    const response = await api.get(`/books/${id}`);
    return response.data;
  },
  createBook: async (bookData, coverImage) => {
    const formData = new FormData();
    formData.append('title', bookData.title);
    formData.append('description', bookData.description);
    formData.append('isbn', bookData.isbn);
    formData.append('author', bookData.author);
    formData.append('genre', bookData.genre);
    formData.append('publishedYear', bookData.publishedYear);
    
    if (coverImage) {
      formData.append('coverImage', coverImage);
    }
    
    const response = await api.post('/books', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },
  updateBook: async (id, bookData, coverImage) => {
    const formData = new FormData();
    formData.append('title', bookData.title);
    formData.append('description', bookData.description);
    formData.append('isbn', bookData.isbn);
    formData.append('author', bookData.author);
    formData.append('genre', bookData.genre);
    formData.append('publishedYear', bookData.publishedYear);
    
    if (coverImage) {
      formData.append('coverImage', coverImage);
    }
    
    const response = await api.put(`/books/${id}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },
  deleteBook: async (id) => {
    await api.delete(`/books/${id}`);
  },
};

export default api; 