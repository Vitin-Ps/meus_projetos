import http from 'http';
import { Server, Socket } from 'socket.io';
import { User } from './interfaces/User';
import { Mensagem } from './interfaces/Mensagem';

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

  socket.on('enviar-notificacao', (user_id: number) => {
    const user: User = users.filter((user) => user.user_id === user_id)[0];
    if (user) {
      socket.to(user.peer).emit('receber-notificacao');
    }
  });

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
