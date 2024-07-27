import { Socket } from 'socket.io-client';
import { Mensagem } from '../interfaces/Mensagem';

export const addCodigoUser = (socket: Socket, codigo: string) => {
  socket.emit('addCodigoUser', codigo);
};

export const addMensagem = (socket: Socket, data: Mensagem) => {
  socket.emit('addMensagem', data);
};

export const entrarSala = (socket: Socket, codSala: String) => {
  socket.emit('entrarSala', codSala);
};
