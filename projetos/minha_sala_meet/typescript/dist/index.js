"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const http_1 = __importDefault(require("http"));
const PORT = process.env.PORT || 3002;
const app = (0, express_1.default)();
const server = http_1.default.createServer(app);
const io = require('socket.io')(server);
app.use(express_1.default.static('public'));
app.get('/', (req, res) => {
    res.sendFile(__dirname + '/public/index.html');
});
let peersConmectados = [];
io.on('connection', (socket) => {
    console.log('Novo usuário conectado: ', socket.id);
    peersConmectados.push(socket.id);
    console.log(peersConmectados);
    socket.on('pedido-chamada', (data) => {
        const { chamadorSocketId, ligacaoTipo } = data;
        const peerConectado = peersConmectados.find((peerSokcketId) => peerSokcketId === chamadorSocketId);
        console.log(peerConectado);
        if (peerConectado) {
            const data = {
                chamadorSocketId: socket.id,
                ligacaoTipo,
            };
            io.to(chamadorSocketId).emit('pedido-chamada', data);
        }
        else {
            const data = {
                pedidoChamadaResposta: 'CHAMADA_NAO_ENCONTRADA',
            };
            io.to(socket.id).emit('pedido-chamada-resposta', data);
        }
    });
    socket.on('pedido-chamada-resposta', (data) => {
        const { chamadorSocketId } = data;
        const peerConectado = peersConmectados.find((peerSokcketId) => peerSokcketId === chamadorSocketId);
        if (peerConectado) {
            io.to(data.chamadorSocketId).emit('pedido-chamada-resposta', data);
        }
    });
    socket.on('sinal-webRTC', (data) => {
        console.log('CHAGANDODODODODDO');
        const { usuarioConectadoSocketId } = data;
        const peerConectado = peersConmectados.find((peerSokcketId) => peerSokcketId === usuarioConectadoSocketId);
        if (peerConectado) {
            io.to(usuarioConectadoSocketId).emit('sinal-webRTC', data);
        }
    });
    socket.on('disconnect', () => {
        console.log('Usuário desconectado');
        const newPeersConectados = peersConmectados.filter((peerSokcketId) => peerSokcketId !== socket.id);
        peersConmectados = newPeersConectados;
        console.log(peersConmectados);
    });
});
server.listen(PORT, () => {
    console.log(`Server iniciado na porta ${PORT}\nPara acessar a aplicação, clique em http://localhost:${PORT}`);
});
