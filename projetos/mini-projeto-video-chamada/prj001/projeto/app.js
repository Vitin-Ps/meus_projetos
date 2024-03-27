const express = require('express');
const app = express();
const port = 4200;
const startSocketServer = require('./src/assets/js/socket');
const io = startSocketServer();

// Iniciar o servidor Socket.IO
// app.use(express.static('src/components'))
app.use(express.static('src/assets'))
app.set('views', 'src/components')
app.set('view engine', 'ejs');
const salas = {}; // Armazena as informações das salas


// Rota para a página home
// app.get('/', (req, res) => {
//   // Renderiza a página index.ejs e passa o conteúdo específico da página home como uma variável
//   res.render('index', { content: 'pages/home.ejs' });
// });

// Rota para a página home
app.get('/chamada', (req, res) => {
  res.render('index', { content: 'pages/sala.ejs' });
});

app.get('/chamada/:codigo', (req, res) => {
  const codigo = req.params.codigo;
  res.render('index', { content: 'pages/sala.ejs', codigo });
});

app.get('/criar_sala', (req, res) => {
  const roomCode = generateUniqueCode(); // Gerar um código único para a sala
  salas[roomCode] = true; // Marcar a sala como existente
  res.json({ roomCode }); // Retorna o código da sala para o frontend
});

app.get('/sala/:roomCode', (req, res) => {
  const roomCode = req.params.roomCode;
  if (!salas[roomCode]) {
      // Se a sala não existe, redirecione para uma página de erro ou crie uma nova sala
      res.redirect('/');
      return;
  }

  // Renderizar a página da sala com o código único
  res.render('index', { content: 'pages/sala.ejs', roomCode });
});
// Inicie o servidor
app.listen(port, () => {
  console.log(`Servidor Express iniciado na porta ${port}`);
});

