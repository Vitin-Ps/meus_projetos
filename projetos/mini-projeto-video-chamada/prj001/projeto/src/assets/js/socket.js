const http = require('http');
const server = http.createServer();
const io = require('socket.io')(server, {cors: {origin: 'http://localhost:4200'}});
const salas = {};
const usuarios = {}; // Objeto para mapear ID do socket para nome do usuário

function startSocketServer() {
    io.on('connection', (socket) => {
        console.log('Novo usuário conectado');
        io.emit("id_usuario", socket.id)

        socket.on('criar_sala', () => {
            const roomCode = socket.id + generateUniqueCode(); 
            socket.join(roomCode); 
            console.log(`Sala criada com código: ${roomCode}`);
            salas[roomCode] = {
                roomCode: roomCode,
            };
            socket.emit('sala_criada', { roomCode }); 
        });
       
        socket.on('entrar_sala', (roomCode) => {
            if (salas[roomCode]) {
                socket.join(roomCode);
                console.log(`Usuário entrou na sala: ${roomCode}`);
            } else {
                console.log(`Tentativa de entrada em sala inválida: ${roomCode}`);
                socket.emit('erro', 'Sala não encontrada ou código inválido.');
            }
        });

        socket.on('enviar_mensagem', (data) => {      
            io.to(data.roomCode).emit('mensagem_recebida', {
                text: data.message,
                idAutor: socket.id,
                autor: usuarios[socket.id], // Obtém o nome do usuário usando o ID do socket
                horario: new Date().toLocaleTimeString()
            }); 
        });

        socket.on('set_nome', nome => {
            usuarios[socket.id] = nome; // Armazena o nome do usuário no objeto 'usuarios' usando o ID do socket
        });
               
        socket.on('disconnect', () => {
            console.log('O usuário se desconectou.');
            delete usuarios[socket.id]; // Remove o nome do usuário ao desconectar
        });
    });

    // Iniciar o servidor na porta desejada
    const port = 3000;
    server.listen(port, () => {
        console.log(`Servidor Socket.IO iniciado na porta ${port}`);
    });

    return io; // Retornar o objeto io para ser usado em outros arquivos
}

function generateUniqueCode() {
    // Implementação para gerar um código único, por exemplo, usando um timestamp
    return Date.now().toString(36);
}

module.exports = startSocketServer; // Exportar a função startSocketServer
