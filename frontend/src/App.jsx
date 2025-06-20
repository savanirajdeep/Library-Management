import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext.jsx';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Navbar from './components/Navbar.jsx';
import Login from './pages/Login.jsx';
import Register from './pages/Register.jsx';
import BookList from './pages/BookList.jsx';
import BookForm from './pages/BookForm.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import 'bootstrap/dist/css/bootstrap.min.css';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <Router>
          <div className="app">
            <Navbar />
            <div className="container mt-4">
              <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route
                  path="/"
                  element={
                    <ProtectedRoute>
                      <BookList />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/books/new"
                  element={
                    <ProtectedRoute requireAdmin>
                      <BookForm />
                    </ProtectedRoute>
                  }
                />
                <Route
                  path="/books/edit/:id"
                  element={
                    <ProtectedRoute requireAdmin>
                      <BookForm />
                    </ProtectedRoute>
                  }
                />
              </Routes>
            </div>
          </div>
        </Router>
      </AuthProvider>
    </QueryClientProvider>
  );
}

export default App;
