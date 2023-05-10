// Conexão

const express = require('express');
const { engine } = require('express-handlebars');
const bodyParser = require('body-parser');

//App
const app = express();
app.use(bodyParser.urlencoded({ extended: true }));
// Tabelas do BD
const vendas = require('./models/vendas');

app.engine('handlebars', engine({
    defaultLayout: 'main',
    runtimeOptions: {
        allowProtoMethodsByDefault: true,
        allowProtoPropertiesByDefault: true
    },
    
}));
app.set('view engine', 'handlebars');
app.set('views', './views');

// ------------------------- Rotas --------------------

app.get('/cadastro-vendas', (req, res) => {
    res.render('cadastro-vendas');
})

app.post('/add-venda', (req, res) => {
    // res.send("Nome: " + req.body.nomeFunc + "<br>Valor: " + req.body.valorVenda + "<br>");
    const valorVenda = parseFloat(req.body.valorVenda);
    const desconto = valorVenda * 0.1; // Calcula o valor do desconto como 10% do valor da venda
    vendas.create({
        nomeFunc: req.body.nomeFunc,
        valorVenda: valorVenda,
        desconto: desconto
    }).then(function(){
        res.redirect("cadastro-vendas");
    }).catch(function(erro){
        res.send("Erro ao enviar as informações " + erro);
    });
});


app.listen(8081);