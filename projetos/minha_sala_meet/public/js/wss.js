import * as ui from './ui.js';
import * as store from './store.js';
import * as webRTCHandler from './webRTCHandler.js';

let socketIO = null;

export const registraSocketEventos = (socket) => {
  socketIO = socket;
  socket.on('connect', () => {
    console.log('Usuário conectado com sucesso.', socket.id);
    store.setSocketId(socket.id);
    ui.atualizaCodUnico(socket.id);
  });

  socket.on('pedido-chamada', (data) => {
    webRTCHandler.executaPedidoChamada(data);
  });

  socket.on('pedido-chamada-resposta', (data) => {
    webRTCHandler.executaPedidoChamadaResposta(data);
  });
};

export const enviarPedidoChamada = (data) => {
  socketIO.emit('pedido-chamada', data);
};

export const enviarPedidoChamadaResposta = (data) => {
  socketIO.emit('pedido-chamada-resposta', data);
};
