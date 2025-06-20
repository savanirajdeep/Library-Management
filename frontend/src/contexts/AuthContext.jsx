import { createContext, useContext, useState, useEffect } from 'react';
import { authService } from '../services/api';

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    const response = await authService.login(credentials);
    console.log('Login response:', response);
    const userData = {
      name: response.name,
      email: response.email,
      role: response.role
    };
    console.log('User data:', userData);
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(userData));
    setUser(userData);
    return response;
  };

  const register = async (userData) => {
    const response = await authService.register(userData);
    console.log('Register response:', response);
    const user = {
      name: response.name,
      email: response.email,
      role: response.role
    };
    localStorage.setItem('token', response.token);
    localStorage.setItem('user', JSON.stringify(user));
    setUser(user);
    return response;
  };

  const logout = () => {
    authService.logout();
    setUser(null);
  };

  const isAdmin = () => {
    const adminStatus = user?.role === 'ADMIN';
    console.log('isAdmin check - user:', user, 'role:', user?.role, 'isAdmin:', adminStatus);
    return adminStatus;
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, isAdmin, loading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
}; 