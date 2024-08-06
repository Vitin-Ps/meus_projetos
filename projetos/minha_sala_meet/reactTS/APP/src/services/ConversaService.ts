import api, { verificaErroApi } from './api';
import { gerarUUID } from './FuncionalidadesService';

export async function cadastrarPrivado(user_one_id: number, user_two_id: number) {
  const dadosJSON: string = JSON.stringify({
    user_one_id,
    user_two_id,
    uuid: gerarUUID(),
  });

  try {
    const res = await api.post('/chat/privateuser', dadosJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarConversasPorUserId(id: number) {
  try {
    const res = await api.get(`/chat/user/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function detalhaConversaPorUserId(user_one_id: number, conversa_id: number) {
  const dadosJSON: string = JSON.stringify({
    user_one_id,
    conversa_id,
  });

  try {
    const res = await api.post('/chat', dadosJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function deletaPrivado(user_one_id: number, user_two_id: number) {
  const grupoJSON: string = JSON.stringify({
    user_one_id,
    user_two_id,
  });

  try {
    const res = await api.post('/chat/privateuser/del', grupoJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
