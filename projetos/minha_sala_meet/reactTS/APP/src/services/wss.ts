import { Mensagem } from '../interfaces/Mensagem';
import { Solicitacao } from '../interfaces/Solicitacao';
import { Amigo } from '../interfaces/Amigo';
import { Grupo } from '../interfaces/Grupo';
import { socket } from './socket';
import { Usuario } from '../interfaces/Usuario';

export const addCodigoUser = (codigo: string) => {
  socket.emit('addCodigoUser', codigo);
};

export const addMensagem = (data: Mensagem) => {
  socket.emit('addMensagem', data);
};

export const entrarSala = (codSala: String) => {
  socket.emit('entrarSala', codSala);
};

export const enviarNotificacao = (notificacao: Solicitacao) => {
  socket.emit('enviar-notificacao', notificacao);
};

export const amigoEvent = (type: string, amigo: Amigo) => {
  socket.emit('amigo-event', {
    type,
    amigo,
  });
};

export const grupoEvent = (uuid: string, grupo: Grupo, type: string, user?: Usuario) => {
  socket.emit('grupo-event', {
    uuid,
    grupo,
    type,
    user,
  });
};
