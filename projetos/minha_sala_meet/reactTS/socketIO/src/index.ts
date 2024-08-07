import http from 'http';
import { Server, Socket } from 'socket.io';
import { User } from './interfaces/User';
import { Mensagem } from './interfaces/Mensagem';
import { Solicitacao } from './interfaces/Solicitacao';

const PORT = process.env.PORT || 3005;

// Crie o servidor HTTP sem a necessidade de usar o Express
const server = http.createServer();
const io = new Server(server, {
  cors: {
    origin: '*',
    methods: ['GET', 'POST'],
  },
});

let users: User[] = [];

io.on('connection', (socket: Socket) => {
  console.log(socket.id);

  socket.on('conectar', (user_id: number) => {
    let user: User | undefined = users.find((user) => user.user_id === user_id);

    if (!user) {
      users.push({
        user_id,
        peer: socket.id,
      });
      console.log('Users Conectados: ', users);
    } else console.log('usuário já conectado');
  });

  socket.on('entrarSala', (codSala: string) => {
    socket.join(codSala);
    console.log(`Usuário ${socket.id} entrou na sala ${codSala}`);
  });

  // Lida com o evento 'addMensagem'
  socket.on('addMensagem', (data: Mensagem) => {
    socket.to(data.conversa.uuid).emit('receberMensagem', data);
  });

  socket.on('enviar-notificacao', (notificacao: Solicitacao) => {
    const user: User = users.filter((user) => user.user_id === notificacao.userDestinatario.id!)[0];
    if (user) {
      socket.to(user.peer).emit('receber-notificacao', notificacao);
    }
  });

  socket.on('amigo-event', (data) => {
    const user: User = users.filter((user) => user.user_id === data.amigo.user.id!)[0];
    if (user) {
      socket.to(user.peer).emit('receber-amigo-event', data);
    }
  });

  socket.on('conversa-event', (data) => {
    const user: User = users.filter((user) => user.user_id === Number(data.uuid))[0];
    if (data.type === 'del-chat') {
      socket.to(data.uuid).emit('receber-conversa-event', data);
      socket.emit('receber-conversa-event', data);
    } else if (data.type === 'sair-chat') {
      socket.emit('receber-conversa-event', data);

      const dataGrupo = data;
      dataGrupo.type = 'remover-membro';
      socket.to(data.uuid).emit('receber-conversa-event', dataGrupo);
    } else {
      if (user) {
        socket.to(user.peer).emit('receber-conversa-event', data);
      }
    }
  });

  // socket.on('grupo-event', (data) => {
  //   console.log('Received grupo-event data:', data);
  //   const user: User = users.filter((user) => user.user_id === Number(data.uuid))[0];
  //   if (user) {
  //     socket.to(user.peer).emit('teste-chegou', { message: 'Hello from server!' });
  //   }
  //   // socket.emit('teste-chegou', { message: 'Hello from server!' });
  // });

  // Lida com o evento 'disconnect'
  socket.on('disconnect', () => {
    console.log('Usuário desconectado');

    users = users.filter((user) => user.peer !== socket.id);
    console.log('Users Conectados: ', users);
  });
});

server.listen(PORT, () => {
  console.log(`Server iniciado na porta ${PORT}`);
});
