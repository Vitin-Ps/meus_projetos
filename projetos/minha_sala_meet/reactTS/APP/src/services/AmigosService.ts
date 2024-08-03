import api, { verificaErroApi } from './api';
import { gerarUUID } from './FuncionalidadesService';

export async function enviarSoliticatacao(user_remetente_id: number, user_destinatario_id: number) {
  const dadosJSON: string = JSON.stringify({
    user_remetente_id,
    user_destinatario_id,
  });

  try {
    const res = await api.post('/friends/preoffer', dadosJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarSolicitacoesPorUserId(id: number) {

  try {
    const res = await api.get(`/friends/preoffer/all/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function cadastrarAmigo(user_id: number, amigo_id: number) {
  const dadosJSON: string = JSON.stringify({
    user_id,
    amigo_id,
  });

  try {
    const res = await api.post('/friends', dadosJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarAmigos(id: number) {

  try {
    const res = await api.get(`/friends/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function rejeitarSolicitacao(id: number) {

  try {
    const res = await api.delete(`/friends/preoffer/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function desfazerAmizade(user_id: number, amigo_id: number) {
  const dadosJSON: string = JSON.stringify({
    user_id,
    amigo_id,
  });

  try {
    const res = await api.post('/friends/del', dadosJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}