import axios from 'axios';
import { getToken } from './TokenService';
import { ErrorMessage } from '../interfaces/ErrorMessage';

export function getBaseUrl() {
  return process.env.REACT_APP_API;
}

const api = axios.create({
  baseURL: getBaseUrl(),
});

api.interceptors.request.use(
  async (config) => {
    try {
      const token = getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    } catch (error) {
      console.error('Error retrieving token:', error);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export const verificaErroApi = (error: any) => {
  const errorMessage: ErrorMessage = { error: '', message: '' };

  if (axios.isAxiosError(error)) {
    errorMessage.error = error.code!;
    if (error.response && error.response.data) {
      if (error.response.data.length > 0) {
        errorMessage.message = construirMensagemDeErro(error.response.data);
      } else {
        errorMessage.message = error.response.data.toString();
      }
    } else {
      errorMessage.message = 'Erro na conexão com a API. Tente novamente mais tarde';
    }
    return errorMessage;
  }

  // Função para converter o array de erros em uma string de mensagens
  function construirMensagemDeErro(erros: { campo: string; mensagem: string }[]): string {
    if (erros.map) {
      const mensagens = erros.map((erro) => `${erro.campo}: ${erro.mensagem}`);
      return mensagens.join('\n');
    }
    return erros.toString();
  }

  errorMessage.error = 'Erro desconhecido';
  errorMessage.message = error.message;
  return errorMessage;
};

export default api;
