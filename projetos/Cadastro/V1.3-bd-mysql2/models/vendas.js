//Tabela vendas

//criando tabela 
const mysql = require('mysql2');
const conexao = mysql.createConnection({ // cria a conexão com o banco de dados
    host:'localhost',
    user: 'MailtoDesenvolper',
    password: '1234',
    database: 'vendasBd'
});

// const conexao = require('./db');


function criarVendas() {
    conexao.query(`
    create table vendas(
    idFunc int primary key auto_increment,
    nomeFunc varchar(80) not null,
    valorVenda decimal(10,2) not null,
    desconto decimal(10,2)
    );
    `);
}

function excluirVendas() {
    conexao.query(`
    drop table vendas;
    `).then(() => {
        console.log("Tabela Excluida");
    }).catch((erro) => {
        console.log("Erro ao Excluir Tabela! " + erro);
    });
}

async function editarVendas(idFunc) {
    const [rows, fields] = await conexao.promise().query(`
        select * 
        from vendas 
        where idFunc = ${idFunc};
    `);
    return rows;
}


function addVendas(nomeFunc, valorVenda, res) {
    let desconto = (valorVenda * 0.1).toFixed(2);
    try {
        conexao.query(`
        insert into vendas (
            nomeFunc,
            valorVenda,
            desconto
        )values (
            '${nomeFunc}',
            ${valorVenda},
            ${desconto}
        );
        `);
        console.log("Venda adicionada com sucesso!");
      } catch (erro) {
        res.send("Ocorreu um erro ao adicionar a venda:", erro);
      }
}


async function listarVendas(){
    const [rows, fields] = await conexao.promise().query(`select * from vendas`); 
    return rows;
}

function delVendas(idFunc) {
    conexao.query(`
    delete from vendas where idFunc = ${idFunc}
    `);
}

function altVendas(nomeFunc, valorVenda, idFunc) {
    let desconto = (valorVenda * 0.1).toFixed(2);
    conexao.query(`
      UPDATE vendas
      SET nomeFunc = '${nomeFunc}', valorVenda = ${valorVenda}
      WHERE idFunc = ${idFunc}`
    );
  }

function limparVendas(){
    conexao.query(`delete from vendas`);
}
  
  

module.exports = {
    criarVendas: criarVendas,
    excluirVendas: excluirVendas,
    listarVendas: listarVendas,
    addVendas: addVendas,
    delVendas: delVendas,
    editarVendas: editarVendas,
    altVendas: altVendas,
    limparVendas: limparVendas
}







