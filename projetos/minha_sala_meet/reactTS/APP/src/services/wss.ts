let socketIO: any = null;

export const registraSocketEventos = (socket: any) => {
  socketIO = socket;
  socket.on('connect', () => {
    console.log('Usuário conectado com sucesso.', socket.id);
  });

  socket.on('receberMensagem', (message: string) => {
    console.log("A mensagem chegou: ", message)
  });
};

export const addCodigoUser = (codigo: string) => {
    socketIO.emit('addCodigoUser', codigo);
  };

export const addMensagem = (data: any) => {
  socketIO.emit('addMensagem', data.message, data.codigo);
};

