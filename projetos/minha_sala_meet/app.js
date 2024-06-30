const express = require('express');
const http = require('http');

const PORT = process.env.PORT || 3001;

const app = express();
const server = http.createServer(app);
const io = require('socket.io')(server);

app.use(express.static('public'));

app.get('/', (req, res) => {
  res.sendFile(__dirname + 'public/index.html');
});

let peersConmectados = [];

io.on('connection', (socket) => {
  console.log('Novo usuário conectado: ', socket.id);
  peersConmectados.push(socket.id);
  console.log(peersConmectados);

  socket.on('pedido-chamada', (data) => {
    const { cod_unico_ligacao, ligacaoTipo } = data;
    const peerConectado = peersConmectados.find((peerSokcketId) => peerSokcketId === cod_unico_ligacao);

    console.log(peerConectado);

    if (peerConectado) {
      const data = {
        ligacaoSocketId: socket.id,
        ligacaoTipo,
      };

      io.to(cod_unico_ligacao).emit('pedido-chamada', data);
    } else {
      const data = {
        pedidoChamadaResponsdida: 'CHAMADA_NAO_ENCONTRADA',
      };

      io.to(socket.id).emit('pedido-chamada-respondida', data);
    }
  });

  socket.on('pedido-chamada-resposta', (data) => {
    const { chamadorSocketId } = data;

    const peerConectado = peersConmectados.find((peerSokcketId) => peerSokcketId === chamadorSocketId);

    if (peerConectado) {
      io.to(data.chamadorSocketId).emit('pedido-chamada-resposta', data);
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
