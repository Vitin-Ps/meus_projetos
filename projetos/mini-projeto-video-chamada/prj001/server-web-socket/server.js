const WebSocket = require('ws');

// Crie um novo servidor WebSocket na porta 3000
const wss = new WebSocket.Server({ port: 3000 });

// Evento de conexão: é chamado quando um cliente se conecta
wss.on('connection', function connection(ws) {
  console.log('Novo cliente conectado.');

  // Evento de mensagem: é chamado quando uma mensagem é recebida de um cliente
  ws.on('message', function incoming(message) {
    console.log('Mensagem recebida:', message);

    // Enviar uma mensagem de volta para o cliente
    ws.send('Mensagem recebida pelo servidor: ' + message);
  });

  // Evento de fechamento: é chamado quando a conexão com um cliente é fechada
  ws.on('close', function close() {
    console.log('Cliente desconectado.');
  });
});

console.log('Servidor WebSocket iniciado na porta 3000.');