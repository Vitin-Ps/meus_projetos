const mysql = require('mysql2');
const conexao = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '',
  database: 'db_api_express',
});

module.exports = {
  conexao,
};
