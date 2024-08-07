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
let salas = [];
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
    socket.on('entrarSala', (conversas) => {
        conversas.forEach((conversa) => {
            const uuid = conversa.tipo === 'GRUPO' ? conversa.grupo.conversa.uuid : conversa.privado.conversa.uuid;
            let sala = salas.find((sala) => sala.uuid === uuid);
            const user = users.find((user) => user.peer === socket.id);
            if (!sala) {
                sala = {
                    id: conversa.id,
                    uuid,
                    membros: [],
                    type: conversa.tipo,
                };
                sala.membros.push(user);
                salas.push(sala);
            }
            else {
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
    socket.on('addMensagem', (data) => {
        socket.to(data.conversa.uuid).emit('receberMensagem', data);
    });
    socket.on('enviar-notificacao', (notificacao) => {
        const user = users.find((user) => user.user_id === notificacao.userDestinatario.id);
        if (user) {
            socket.to(user.peer).emit('receber-notificacao', notificacao);
        }
    });
    socket.on('amigo-event', (data) => {
        const user = users.find((user) => user.user_id === data.amigo.user.id);
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
                }
                else {
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
