const app = require('express')();
const server = require('http').createServer(app);
const io = require('socket.io')(server, {cors: {origin: 'http://localhost:4200'}});

const port = 3001;

const salas = {};

io.on('connection', socket => {
    console.log('Usuário conectado: ', socket.id);
    io.to(socket.id).emit('receber_id', socket.id); // Envia o ID apenas para o usuário conectado
    
    socket.on('entrar_sala', salaId => {
        socket.join(salaId); // O socket entra na sala específica
        if (!salas[salaId]) {
            salas[salaId] = []; // Cria um array para armazenar os sockets dessa sala, se ainda não existir
        }
        salas[salaId].push(socket); // Adiciona o socket ao array de sockets da sala
        console.log('Usuário entrou na sala: ', salaId);
    });

      socket.on('disconnect', () => {
        console.log('Usuário desconectado: ', socket.id);
        // Remover o socket das salas que ele estava
        Object.keys(salas).forEach(salaId => {
            salas[salaId] = salas[salaId].filter(s => s !== socket);
        });
    });

    socket.on('mensagem', ({ salaId, message }) => {
        console.log(`Mensagem recebida na sala ${salaId}: `, message);
        // Envia a mensagem apenas para os sockets da sala específica
        io.to(salaId).emit('receber_mensagem', {
            text: message,
            idAutor: socket.id,
            autor: socket.data.nome,
            horario: new Date().toLocaleTimeString()
        });
    });
    
    socket.on('set_nome', nome => {
        socket.data = { nome }; // Armazena o nome do usuário no objeto de dados do socket
    });


});

server.listen(port, () => console.log('Servidor rodando na porta', port));
