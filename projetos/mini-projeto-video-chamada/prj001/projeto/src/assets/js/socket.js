const http = require('http');
const server = http.createServer();
const io = require('socket.io')(server, {cors: {origin: 'http://localhost:4200'}});
const salas = {};
// Função para iniciar o servidor Socket.IO
function startSocketServer() {

    // Evento de conexão de um cliente
    io.on('connection', (socket) => {
        console.log('Novo usuário conectado');
        io.emit("id_usuario", socket.id)

        // Evento para criar uma nova sala
        socket.on('criar_sala', () => {
            const roomCode = socket.id; // Gerar um código único para a sala
            socket.join(roomCode); // O cliente atual se junta à sala com o código único
            console.log(`Sala criada com código: ${roomCode}`);
            salas[roomCode] = {
                roomCode: roomCode,
                // Outras informações relevantes da sala podem ser armazenadas aqui
            };
            socket.emit('sala_criada', { roomCode }); // Emitir evento de confirmação para o cliente
        });

        // Evento para lidar com a entrada do usuário em uma sala existente
        socket.on('entrar_sala', (roomCode) => {
            // Verifique se o código da sala existe na estrutura de dados das salas
            if (salas[roomCode]) {
                // Se o código da sala for válido, o cliente atual pode se juntar à sala
                socket.join(roomCode);
                console.log(`Usuário entrou na sala: ${roomCode}`);
            } else {
                // Se o código da sala não for válido, você pode lidar com isso aqui
                console.log(`Tentativa de entrada em sala inválida: ${roomCode}`);
                // Por exemplo, você pode emitir um evento de erro de volta para o cliente
                socket.emit('erro', 'Sala não encontrada ou código inválido.');
            }
        });

        // Evento para enviar mensagens para uma sala específica
        socket.on('enviar_mensagem', (data) => {
            io.to(data.roomCode).emit('mensagem_recebida', data.message); // Emitir mensagem para todos os clientes na sala
        });

                // Evento 'disconnect' para lidar com a desconexão do usuário
        socket.on('disconnect', () => {
            console.log('O usuário se desconectou.');
            // Adicione qualquer lógica que você deseja executar quando o usuário se desconectar
        });
    });

    // Função para gerar um código único
    function generateUniqueCode() {
        return Date.now().toString(36); // Implementação para gerar um código único, por exemplo, usando um timestamp
    }

    // Iniciar o servidor na porta desejada
    const port = 3000;
    server.listen(port, () => {
        console.log(`Servidor Socket.IO iniciado na porta ${port}`);
    });

    return io; // Retornar o objeto io para ser usado em outros arquivos
}

module.exports = startSocketServer; // Exportar a função startSocketServer
