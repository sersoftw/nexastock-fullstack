import { createContext, ReactNode, useContext, useMemo, useState } from 'react';
import api from '../services/api';
import { AuthResponse } from '../types';

interface AuthContextValue {
  user: AuthResponse | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

function loadUser(): AuthResponse | null {
  const raw = localStorage.getItem('nexastock_user');
  return raw ? JSON.parse(raw) : null;
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthResponse | null>(loadUser());

  async function login(email: string, password: string) {
    const { data } = await api.post<AuthResponse>('/auth/login', { email, password });
    localStorage.setItem('nexastock_token', data.token);
    localStorage.setItem('nexastock_user', JSON.stringify(data));
    setUser(data);
  }

  function logout() {
    localStorage.removeItem('nexastock_token');
    localStorage.removeItem('nexastock_user');
    setUser(null);
  }

  const value = useMemo(
    () => ({ user, login, logout, isAuthenticated: Boolean(user) }),
    [user]
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth debe usarse dentro de AuthProvider');
  }
  return context;
}
