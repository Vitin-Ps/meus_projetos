import http from 'http';
import { Server, Socket } from 'socket.io';
import { User } from './interfaces/User';

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
  console.log('Usuários: ', users);
  // Lida com o evento 'addCodigoUser'
  socket.on('addCodigoUser', (codigo: string) => {
    let user: User | undefined = users.find((user) => user.codigo === codigo);

    if (!user) {
      user = {
        codigo,
        peer: socket.id,
      };

      users.push(user);

      console.log('Usuário Conectado: ', users);
    } else {
      console.log('usuário já conectado');
    }
  });

  // Lida com o evento 'addMensagem'
  socket.on('addMensagem', (message: string, codigo: string) => {
    console.log('chegou');
    const user = users.find((user) => user.codigo === codigo);
    const socketUser: string | null = user ? user.peer : null;

    if (socketUser) {
      io.to(socketUser).emit('receberMensagem', message);
    }
  });

  // Lida com o evento 'disconnect'
  socket.on('disconnect', () => {
    console.log('Usuário desconectado');

    // peersConectados = peersConectados.filter((peerSocketId) => peerSocketId !== socket.id);
    // console.log('Peers Conectados: ', peersConectados);
  });
});

server.listen(PORT, () => {
  console.log(`Server iniciado na porta ${PORT}`);
});
