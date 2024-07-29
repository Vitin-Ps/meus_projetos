import { Mensagem, MensagemJSON } from '../interfaces/Mensagem';
import api, { verificaErroApi } from './api';

export async function inserirMensagem(mensagem: Mensagem) {
  if (!mensagem) {
    return;
  }

  const mensagemEnvio: MensagemJSON = {
    grupo_id: mensagem.grupo.id!,
    usuario_id: mensagem.usuario.id!,
    mensagem: mensagem.mensagem,
    data: formatarData(mensagem.data),
  };

  const mensagemJSON: string = JSON.stringify(mensagemEnvio);

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
    const res = await api.get(`/mensagem/group/${id}`);
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
