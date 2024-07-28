import { useState } from 'react';
import { AuthContext } from './AuthContext';
import { Usuario } from '../../interfaces/Usuario';

export const AuthProvider = ({ children }: { children: JSX.Element }) => {
  const [user, setUser] = useState<Usuario | null>(null);
  const singin = (email: string, password: string) => {
    
  };

  const singout = () => {};
  return <AuthContext.Provider value={{user, singin, singout}}>{children}</AuthContext.Provider>;
};
