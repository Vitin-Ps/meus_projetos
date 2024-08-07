import http from 'http';
import { Server, Socket } from 'socket.io';
import { User } from './interfaces/User';
import { Mensagem } from './interfaces/Mensagem';
import { Solicitacao } from './interfaces/Solicitacao';
import { ConversaTipos } from './interfaces/Conversa';
import { Sala } from './interfaces/Sala';

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
let salas: Sala[] = [];

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

  socket.on('entrarSala', (conversas: ConversaTipos[]) => {
    conversas.forEach((conversa) => {
      const uuid = conversa.tipo === 'GRUPO' ? conversa.grupo!.conversa.uuid : conversa.privado!.conversa.uuid;

      let sala = salas.find((sala: { uuid: string }) => sala.uuid === uuid);
      const user = users.find((user) => user.peer === socket.id)!;

      if (!sala) {
        sala = {
          id: conversa.id!,
          uuid,
          membros: [],
          type: conversa.tipo,
        };

        sala.membros.push(user);
        salas.push(sala);
      } else {
        if (sala.membros.includes(user)) {
          console.log('usuário já conectado na sala!');
          return;
        }
        sala.membros.push(user);
      }
      socket.join(uuid);

      console.log(`Usuário ${socket.id} entrou na sala ${sala.uuid}`);
    });

    console.log('Salas: ', salas);
  });

  // Lida com o evento 'addMensagem'
  socket.on('addMensagem', (data: Mensagem) => {
    socket.to(data.conversa.uuid).emit('receberMensagem', data);
  });

  socket.on('enviar-notificacao', (notificacao: Solicitacao) => {
    const user = users.find((user) => user.user_id === notificacao.userDestinatario.id!);
    if (user) {
      socket.to(user.peer).emit('receber-notificacao', notificacao);
    }
  });

  socket.on('amigo-event', (data) => {
    const user = users.find((user) => user.user_id === data.amigo.user.id!);
    if (user) {
      socket.to(user.peer).emit('receber-amigo-event', data);
    }
  });

  socket.on('conversa-event', (data) => {
    const user = users.find((user) => user.user_id === Number(data.uuid));

    switch (data.type) {
      case 'del-chat':
        socket.to(data.uuid).emit('receber-conversa-event', data);
        socket.emit('receber-conversa-event', data);
        break;
      case 'sair-chat':
        if (user) {
          socket.to(user.peer).emit('receber-conversa-event', data);
        } else {
          socket.emit('receber-conversa-event', data);

          const dataGrupo = data;
          dataGrupo.type = 'remover-membro';
          socket.to(data.uuid).emit('receber-conversa-event', dataGrupo);
        }
        break;
      default:
        if (user) {
          socket.to(user.peer).emit('receber-conversa-event', data);
        }
        break;
    }
  });
  // Lida com o evento 'disconnect'
  socket.on('disconnect', () => {
    console.log('Usuário desconectado');

    users = users.filter((user) => user.peer !== socket.id);
    console.log('Users Conectados: ', users);

    salas.forEach((sala) => {
      sala.membros = sala.membros.filter((membro) => membro.peer !== socket.id);
      socket.leave(sala.uuid);
    });

    salas = salas.filter((sala) => sala.membros.length > 0);

    console.log('Salas restantes: ', salas);
  });
});

server.listen(PORT, () => {
  console.log(`Server iniciado na porta ${PORT}`);
});
