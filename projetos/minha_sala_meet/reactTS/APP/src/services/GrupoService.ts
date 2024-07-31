import { Grupo } from '../interfaces/Grupo';
import api, { verificaErroApi } from './api';
import { gerarUUID } from './FuncionalidadesService';

export async function cadastrarGrupo(nome: string, user_id:number) {


  const grupoJSON: string = JSON.stringify({
    nome,
    uuid: gerarUUID(),
    user_id
  });

  try {
    const res = await api.post('/group', grupoJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarGruposPorUser(id: number) {
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/group/user/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function detalharGrupo(id: number) {
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/group/${id}`);
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
    const res = await api.post('/group/del', grupoJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
