import api, { verificaErroApi } from './api';

export async function addIntegranteGrupo(grupo_id: number, membro_id: number, user_id: number) { 

  const listaJson: string = JSON.stringify({
    user_id,
    grupo_id,
    membro_id,
  });

  try {
    const res = await api.post('/list', listaJson, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarMembrosPorGrupo(id: number) {
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/list/group/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function removeMembroGrupo(membro_id: number, user_id: number, grupo_id: number) {
  const membroJSON: string = JSON.stringify({
    membro_id,
    user_id,
    grupo_id,
  });

  try {
    const res = await api.post('/list/del', membroJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function detalharUserAutoridade(user_id: number, grupo_id: number) {
  const membroJSON: string = JSON.stringify({
    user_id,
    grupo_id,
  });

  try {
    const res = await api.post('/list/user', membroJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
