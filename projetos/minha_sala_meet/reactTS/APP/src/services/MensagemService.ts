import { Mensagem } from '../interfaces/Mensagem';
import api, { verificaErroApi } from './api';

export async function inserirMensagem(mensagem: Mensagem) {
  if (!mensagem) {
    return;
  }

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
  if (!id) {
    return;
  }

  try {
    const res = await api.get(`/mensagem/chat/${id}`);
    return res.data;
  } catch (error) {
    return verificaErroApi(error);
  }
}

// export async function detalharGrupo(id: number) {
//   if (!id) {
//     return;
//   }

//   try {
//     const res = await api.get(`/group/${id}`);
//     return res.data;
//   } catch (error) {
//     return verificaErroApi(error);
//   }
// }

export const formatarData = (date: Date) => {
  const dia = String(date.getDate()).padStart(2, '0');
  const mes = String(date.getMonth() + 1).padStart(2, '0');
  const ano = date.getFullYear();
  const horas = String(date.getHours()).padStart(2, '0');
  const minutos = String(date.getMinutes()).padStart(2, '0');

  return `${dia}/${mes}/${ano} ${horas}:${minutos}`;
};
