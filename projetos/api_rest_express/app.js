const express = require('express');
const ProdutoDAO = require('./src/model/DAO/ProdutoDAO.js');

const app = express();
app.use(express.json());

app.get('/produtos', async (req, res) => {
  try {
    const produtos = await ProdutoDAO.listarProdutos();
    res.json(produtos);
  } catch (erro) {
    res.status(500).send({ message: 'Erro ao listar produtos', erro });
  }
});

app.get('/produtos/:id', async (req, res) => {
  try {
    const produto = await ProdutoDAO.editarProdutos(req.params.id);
    if (produto.length === 0) {
      res.status(404).send({ message: 'Produto não encontrado' });
    } else {
      res.json(produto[0]);
    }
  } catch (erro) {
    res.status(500).send({ message: 'Erro ao buscar produto', erro });
  }
});

app.post('/produtos', async (req, res) => {
  const { nome, valor } = req.body;
  if (!nome || !valor) {
    return res.status(400).send({ message: 'Nome e valor são obrigatórios' });
  }
  try {
    await ProdutoDAO.addProduto(nome, valor);
    res.status(201).send({ message: 'Produto adicionado com sucesso' });
  } catch (erro) {
    res.status(500).send({ message: 'Erro ao adicionar produto', erro });
  }
});

app.put('/produtos', async (req, res) => {
  const { id, nome, valor } = req.body;
  try {
    await ProdutoDAO.altProdutos(nome, valor, id);
    res.status(200).send({ message: 'Produto atualizado com sucesso' });
  } catch (erro) {
    res.status(500).send({ message: 'Erro ao atualizar produto', erro });
  }
});

app.delete('/produtos/:id', async (req, res) => {
  try {
    await ProdutoDAO.delProdutos(req.params.id);
    res.status(200).send({ message: 'Produto removido com sucesso' });
  } catch (erro) {
    res.status(500).send({ message: 'Erro ao remover produto', erro });
  }
});

const PORT = 8080;
app.listen(PORT, () => {
  console.log(`Servidor rodando na porta ${PORT}`);
});
