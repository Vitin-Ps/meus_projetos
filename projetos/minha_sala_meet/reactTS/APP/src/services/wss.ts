import { Mensagem } from "../interfaces/Mensagem";

let socketIO: any = null;

export const registraSocketEventos = (socket: any) => {
  socketIO = socket;  

  socket.on('receberMensagem', (message: string) => {
    console.log('A mensagem chegou: ', message);
  });
};

export const addCodigoUser = (codigo: string) => {
  socketIO.emit('addCodigoUser', codigo);
};

export const addMensagem = (data: Mensagem) => {
  socketIO.emit('addMensagem', data);
};

export const entrarSala = (codSala: String) => {
  socketIO.emit('entrarGrupo', codSala);
};
