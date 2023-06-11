// Conexão

const express = require('express');
const { engine } = require('express-handlebars');
const bodyParser = require('body-parser');
const moment = require('moment');
const numeral = require('numeral');

//App
const app = express();
// Define a pasta onde estão os arquivos estáticos
app.use('/public', express.static(__dirname + '/public'));
app.use(bodyParser.urlencoded({ extended: true }));
// Tabelas do BD
const Vendas = require('./models/vendas');

app.engine('handlebars', engine({
    defaultLayout: 'main',
    runtimeOptions: {
        allowProtoMethodsByDefault: true,
        allowProtoPropertiesByDefault: true
    },
    helpers: {
        formatDate: (date) => {
            return moment(date).format('DD/MM/YYYY');
        },
    }
    
}));
app.set('view engine', 'handlebars');
app.set('views', './views');

numeral.defaultFormat('0,0.00');

// ------------------------- Rotas --------------------

app.get('/cadastro-vendas', (req, res) => {
    Vendas.findAll().then((vendas) => {
        res.render('cadastro-vendas', {vendas: vendas});
    })
})

app.post('/add-venda', (req, res) => {
    const valorVenda = parseFloat(req.body.valorVenda);
    // const desconto = valorVenda * 0.1;
    const desconto = numeral(valorVenda + 0.1).format();

    Vendas.create({
        nomeFunc: req.body.nomeFunc,
        valorVenda: valorVenda,
        desconto: desconto
    }).then(function(){
        res.redirect("/cadastro-vendas");
    }).catch(function(erro){
        res.send("Erro ao enviar as informações " + erro);
    });
});

app.get('/del-venda/:idFunc', (req, res) => {
    Vendas.destroy({
        where: {'idFunc': req.params.idFunc}
    }).then(() => {
        res.redirect("/cadastro-vendas");
    }).catch((erro) => {
        ("Não Apagado " + erro);
    });
});

app.get('/editar-venda/:idFunc', (req, res) => {
    Vendas.findByPk(req.params.idFunc).then((vendas) => {
        res.render('editar-venda', {idFunc: vendas.idFunc, nomeFunc: vendas.nomeFunc, valorVenda: vendas.valorVenda, desconto: vendas.desconto});
    }).catch((erro) => {
        res.send("Dado não encontrado " + erro);
    });
});

app.post('/alt-venda/:idFunc', (req, res) => {
    const valorVenda = parseFloat(req.body.valorVenda);
    // const desconto = valorVenda * 0.1;
    const desconto = numeral(valorVenda + 0.1).format();

   
    Vendas.update({
        nomeFunc: req.body.nomeFunc,
        valorVenda: valorVenda,
        desconto: desconto
    },
    {
        where: {'idFunc': req.body.idFunc}
    }).then(() => {
        res.redirect("/cadastro-vendas");
    }).catch((erro) => {
        res.send("Não Alterado " + erro);
    });
})

app.get('/limpar-tabela-vendas', (req, res) => {
    Vendas.destroy({ where: {} }).then(() => {
        res.redirect('/cadastro-vendas');
      }).catch((erro) => {
        res.send('Erro ao excluir registros:', erro);
      });
  });
  

app.listen(8081);