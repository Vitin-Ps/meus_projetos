const db = require('./db');
const vendas = db.sequelize.define('vendas', {
    idFunc: {
        type: db.Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    nomeFunc: {
        type: db.Sequelize.STRING
    },
    valorVenda: {
        type: db.Sequelize.DOUBLE(10,2)
    },
    desconto: {
        type: db.Sequelize.DOUBLE(10,2),
        get() {
            return this.getDataValue('valorVenda') * 0.1; // Calcula o valor do desconto como 10% do valor da venda -- getDataValue --> resgata um valor dentro do parâmetro
        }
    }
});

// vendas.sync({force: true});

// db.sequelize.sync();

module.exports = vendas;

