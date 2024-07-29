import { jwtDecode } from 'jwt-decode';

export interface MeuJwtPayload {
  sub: string;
  role: string;
  id: number;
  exp: number;
}

export function validadeToken(token: string): boolean {
  if (!token) {
    // Se não houver token, consideramos como expirado
    return false;
  }

  const decodedToken = infoToken(token);

  if (!decodedToken || !decodedToken.exp) {
    // Se não houver data de expiração no token, consideramos como expirado
    return false;
  }

  // Obtenha a data de expiração em segundos
  const expiraEmSegundos = decodedToken.exp;

  // Obtenha a data atual em segundos
  const dataAtualEmSegundos = Math.floor(Date.now() / 1000);

  // Verifique se a data de expiração é anterior à data atual
  return expiraEmSegundos > dataAtualEmSegundos;
}

export function infoToken(token: string): MeuJwtPayload | null {
  try {
    const infoToken: MeuJwtPayload = jwtDecode(token);
    return infoToken;
  } catch (error) {
    console.error('Erro ao decodificar o token:', error);
    return null;
  }
}

export const removeToken = () => {
  localStorage.removeItem('token');
};

export const addToken = (token: string) => {
  localStorage.setItem('token', token);
};

export const getToken = () => {
  return localStorage.getItem('token');
};
