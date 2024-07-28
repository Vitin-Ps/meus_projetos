import { createContext } from 'react';
import { Usuario } from '../../interfaces/Usuario';

export type AuthContentType = {
  user: Usuario | null;
  sigin: (email: string, password: string) => Promise<boolean>;
  singout: () => void;
};

export const AuthContext = createContext<AuthContentType>(null!);
