import axios, { AxiosError } from 'axios';
import api, { verificaErroApi } from './api';
import { ErrorMessage } from '../interfaces/ErrorMessage';
import { errorMonitor } from 'events';

export async function fazerLogin(login: string, senha: string) {
  if (!login || !senha) {
    console.log('Email ou senha vazios');
    return null; // Se algum dos dois estiver vazio
  }

  try {
    // Tenta fazer a conexão com a API
    const resultado = await api.post('/login', { login, senha });
    console.log('chegou: ', resultado.data);
    return resultado.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
