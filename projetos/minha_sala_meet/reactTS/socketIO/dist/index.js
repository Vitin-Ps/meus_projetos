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
    console.log('Usuários: ', users);
    // Lida com o evento 'addCodigoUser'
    socket.on('addCodigoUser', (codigo) => {
        let user = users.find((user) => user.codigo === codigo);
        if (!user) {
            user = {
                codigo,
                peer: socket.id,
            };
            users.push(user);
            console.log('Usuário Conectado: ', users);
        }
        else {
            console.log('usuário já conectado');
        }
    });
    // Lida com o evento 'addMensagem'
    socket.on('addMensagem', (message, codigo) => {
        console.log('chegou');
        const user = users.find((user) => user.codigo === codigo);
        const socketUser = user ? user.peer : null;
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
