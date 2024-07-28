import { createContext } from 'react';
import { Usuario } from '../../interfaces/Usuario';

export type AuthContentType = {
  user: Usuario | null;
  signin: (login: string, senha: string) => Promise<boolean>;
  sigout: () => void;
};

export const AuthContext = createContext<AuthContentType>(null!);
