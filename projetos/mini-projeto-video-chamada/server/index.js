const app = require('express')();
const server = require('http').createServer(app);
const io = require('socket.io')(server, {cors: {origin: 'http://localhost:4200'}});

const port = 3001;

io.on('connection', socket => {
    console.log('Usuário conectado: ', socket.id);
    io.to(socket.id).emit('receber_id', socket.id); // Envia o ID apenas para o usuário conectado
    socket.on('disconnect', reason => {
        console.log('Usuário desconectado: ', socket.id);
    });

    socket.on('set_nome', nome => {
        socket.data.nome = nome // Armazena o nome do usuário em socket.data
    });

    socket.on('mensagem', message => {
        console.log('Mensagem recebida: ', message);
        const horarioAtual = new Date();
        io.emit('receber_mensagem', {
            text: message.text,
            idAutor: socket.id,
            autor: socket.data.nome, // Obtém o nome do usuário armazenado em socket.data
            horario: `${horarioAtual.getHours().toString().padStart(2, '0')}:${horarioAtual.getMinutes().toString().padStart(2, '0')}`
        });
    });
});

server.listen(port, () => console.log('Servidor rodando na porta', port));
