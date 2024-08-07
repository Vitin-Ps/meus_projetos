"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const http_1 = __importDefault(require("http"));
const socket_io_1 = require("socket.io");
const PORT = process.env.PORT || 3005;
// Crie o servidor HTTP sem a necessidade de usar o Express
const server = http_1.default.createServer();
const io = new socket_io_1.Server(server, {
    cors: {
        origin: '*',
        methods: ['GET', 'POST'],
    },
});
let users = [];
io.on('connection', (socket) => {
    console.log(socket.id);
    socket.on('conectar', (user_id) => {
        let user = users.find((user) => user.user_id === user_id);
        if (!user) {
            users.push({
                user_id,
                peer: socket.id,
            });
            console.log('Users Conectados: ', users);
        }
        else
            console.log('usuário já conectado');
    });
    socket.on('entrarSala', (codSala) => {
        socket.join(codSala);
        console.log(`Usuário ${socket.id} entrou na sala ${codSala}`);
    });
    // Lida com o evento 'addMensagem'
    socket.on('addMensagem', (data) => {
        socket.to(data.conversa.uuid).emit('receberMensagem', data);
    });
    socket.on('enviar-notificacao', (notificacao) => {
        const user = users.filter((user) => user.user_id === notificacao.userDestinatario.id)[0];
        if (user) {
            socket.to(user.peer).emit('receber-notificacao', notificacao);
        }
    });
    socket.on('amigo-event', (data) => {
        const user = users.filter((user) => user.user_id === data.amigo.user.id)[0];
        if (user) {
            socket.to(user.peer).emit('receber-amigo-event', data);
        }
    });
    socket.on('conversa-event', (data) => {
        const user = users.filter((user) => user.user_id === Number(data.uuid))[0];
        if (data.type === 'del-chat') {
            socket.to(data.uuid).emit('receber-conversa-event', data);
            socket.emit('receber-conversa-event', data);
        }
        else if (data.type === 'sair-chat') {
            socket.emit('receber-conversa-event', data);
            const dataGrupo = data;
            dataGrupo.type = 'remover-membro';
            socket.to(data.uuid).emit('receber-conversa-event', dataGrupo);
        }
        else {
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
