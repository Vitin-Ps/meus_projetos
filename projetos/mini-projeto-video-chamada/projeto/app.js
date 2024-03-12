const express = require('express');
const app = express();
const port = 4200;

// app.use(express.static('src/components'))
app.use(express.static('src/assets'))
app.set('views', 'src/components')
app.set('view engine', 'ejs');


// Rota para a página home
app.get('/', (req, res) => {
  // Renderiza a página index.ejs e passa o conteúdo específico da página home como uma variável
  res.render('index', { content: 'pages/home.ejs' });
});

// Rota para a página home
app.get('/chat', (req, res) => {
  // Renderiza a página index.ejs e passa o conteúdo específico da página home como uma variável
  res.render('index', { content: 'pages/chat.ejs' });
});

// Inicie o servidor
app.listen(port, () => {
  console.log(`Servidor Express iniciado na porta ${port}`);
});
