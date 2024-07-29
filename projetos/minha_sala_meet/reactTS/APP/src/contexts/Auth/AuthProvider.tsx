import React, { useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';
import { Usuario } from '../../interfaces/Usuario';
import { fazerLogin } from '../../services/AuthService';
import { addToken, getToken, infoToken, MeuJwtPayload, removeToken, validadeToken } from '../../services/TokenService';
import { detalhaUsuario } from '../../services/UsuarioService';
import api from '../../services/api';

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<Usuario | null>(null);

  useEffect(() => {
    const validaToken = async () => {
      const token = getToken();

      if (token) {
        try {
          const tokenValidado = validadeToken(token);

          if (tokenValidado) {
            const dadosToken = infoToken(token);
            if (dadosToken) {
              const user = await detalhaUsuario(dadosToken.id);
              if (user) {
                setUser(user);
              }
            }
          }
        } catch (error) {
          console.error('Erro ao validar o token ou obter o usuário:', error);
        }
      }
    };

    validaToken(); // Chama a função assíncrona
  }, [api]);

  const signin = async (login: string, senha: string): Promise<boolean> => {
    const data = await fazerLogin(login, senha);
    if (data?.token) {
      const validaToken = validadeToken(data.token);

      if (validaToken) {
        addToken(data.token);
        const dadosToken = infoToken(data.token);
        if (dadosToken) {
          try {
            const user = await detalhaUsuario(dadosToken.id);
            if (user) {
              setUser(user);
              return true;
            }
          } catch (error) {
            console.log('Erro ao detalhar usuário:', error);
          }
        }
      }
    }
    return false;
  };

  const sigout = () => {
    removeToken();
    setUser(null);
  };

  return <AuthContext.Provider value={{ user, signin, sigout }}>{children}</AuthContext.Provider>;
};
