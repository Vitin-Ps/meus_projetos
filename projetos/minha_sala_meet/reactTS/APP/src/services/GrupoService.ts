import api, { verificaErroApi } from './api';
import { gerarUUID } from './FuncionalidadesService';

export async function cadastrarGrupo(nome: string, user_id:number) {


  const grupoJSON: string = JSON.stringify({
    nome,
    uuid: gerarUUID(),
    user_id
  });

  try {
    const res = await api.post('/chat/group', grupoJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function detalharGrupo(id: number) {

  try {
    const res = await api.get(`/chat/group/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function deletaGrupo(grupo_id: number, user_id: number) {
  const grupoJSON: string = JSON.stringify({
    grupo_id,
    user_id,
  });

  try {
    const res = await api.post('/chat/group/del', grupoJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
