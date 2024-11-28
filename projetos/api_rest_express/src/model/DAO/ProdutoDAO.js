const dbConnection = require('../dbConnection.js');

const addProduto = (nome, valor) => {
  return dbConnection.conexao.promise().query(`
    INSERT INTO produto (nome, valor) VALUES ('${nome}', ${valor});
  `);
};

const editarProdutos = async (id) => {
  const [rows] = await dbConnection.conexao.promise().query(`
    SELECT * FROM produto WHERE id = ${id};
  `);
  return rows;
};

const listarProdutos = async () => {
  const [rows] = await dbConnection.conexao.promise().query(`
    SELECT * FROM produto;
  `);
  return rows;
};

const delProdutos = (id) => {
  return dbConnection.conexao.promise().query(`
    DELETE FROM produto WHERE id = ${id};
  `);
};

const altProdutos = (nome, valor, id) => {
  return dbConnection.conexao.promise().query(`
    UPDATE produto SET nome = '${nome}', valor = ${valor} WHERE id = ${id};
  `);
};

module.exports = {
  addProduto,
  editarProdutos,
  listarProdutos,
  delProdutos,
  altProdutos, 
};
