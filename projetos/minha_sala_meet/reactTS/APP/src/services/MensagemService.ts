import { Mensagem } from '../interfaces/Mensagem';
import api, { verificaErroApi } from './api';
import { formatarData } from './FuncionalidadesService';

export async function inserirMensagem(mensagem: Mensagem) {
  const mensagemJSON: string = JSON.stringify({
    conversa_id: mensagem.conversa.id,
    user_remetente_id: mensagem.userRemetente.id,
    mensagem: mensagem.mensagem,
    data: formatarData(mensagem.data),
  });

  try {
    const res = await api.post('/mensagem', mensagemJSON, {
      headers: { 'Content-Type': 'application/json' },
    });
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

export async function listarMensagensPorGrupo(id: number) {
  try {
    const res = await api.get(`/mensagem/chat/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}
