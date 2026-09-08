import { createContext, useContext, useMemo, useState } from 'react';
import { authService } from '../services/authService';
import { clearStoredUser, getStoredToken, getStoredUser, storeAccessToken, storeUser } from '../utils/storage';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => getStoredUser());
  const [accessToken, setAccessToken] = useState(() => getStoredToken());

  async function login(credentials) {
    const response = await authService.login(credentials);
    persistAuth(response);
    return response;
  }

  async function signup(payload) {
    const response = await authService.signup(payload);
    persistAuth(response);
    return response;
  }

  function persistAuth(response) {
    setUser(response.user);
    setAccessToken(response.accessToken || '');
    storeUser(response.user);
    storeAccessToken(response.accessToken);
  }

  function logout() {
    setUser(null);
    setAccessToken('');
    clearStoredUser();
  }

  const value = useMemo(() => ({
    user,
    accessToken,
    isAuthenticated: Boolean(user && accessToken),
    isAdmin: user?.role === 'ADMIN',
    login,
    signup,
    logout
  }), [user, accessToken]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used inside AuthProvider');
  }
  return context;
}
