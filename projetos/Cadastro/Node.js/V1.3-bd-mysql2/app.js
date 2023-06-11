
//express
const express = require('express');
const app = express();

//bodyParser
const bodyParser = require('body-parser'); // usado para que as informações do cliente(dados que o usuario digitar no formulario) sejão processadas e enviadas para o banco de dados através do Sequelize ou mysql2
app.use(bodyParser.urlencoded({extended: true})); // o método urlencoded é usado para transformar as informações do formulario em um objeto Js para que possa ser trabalhado no node.js. para chamar o dado é usado (req.body.namedoInput)

//Para aceitar arquivos estaticos
app.use('/public', express.static(__dirname + '/public'));

//handlebars
const {engine} = require('express-handlebars');
app.engine('handlebars', engine({
    defaultLayout: 'main', // acessa a pasta /views/layouts e pega o arquivo main.handlebars como html principal, oque for colocado la dentro aplicará para os arquivos do views, basta colocar {{{body}}} dentro do main
    runtimeOptions: { // serve para o handlebars ler os atributos da tabela no bd
        allowProtoMethodsByDefault: true,
        allowProtoPropertiesByDefault: true
    }
}));
app.set('view engine', 'handlebars'); // meio que sincronisa o express para que ele possa usar os modelos do handlebars para visualizações em html
app.set('views', './views'); // ativa o handlebars para buscar na pasta /views os arquivos que vão ser usados como html

const Vendas = require('./models/vendas');
//Vendas.criarVendas(); //usado para criar a tabela(use somente na primeira aplicação)

//rotas
app.get('/cadastro-vendas', async (req, res) => { // aqui abre uma rota no express que cria uma aba no url 
    const vendas = await Vendas.listarVendas();
    res.render('cadastro-vendas', {vendas: vendas}); // renderiza uma página html presente na pasta /views
    console.log(vendas);
    
});

app.post('/add-venda', (req, res) => {
    Vendas.addVendas(req.body.nomeFunc, req.body.valorVenda, res);
    res.redirect('/cadastro-vendas');
});

app.get('/editar-venda/:idFunc', async (req, res) => {
    const vendas = await Vendas.editarVendas(req.params.idFunc);
    const venda = vendas[0]; // obtenha o primeiro elemento do array
    res.render('editar-venda', {idFunc: venda.idFunc, nomeFunc: venda.nomeFunc, valorVenda: venda.valorVenda, desconto: venda.desconto});
});

app.post('/alt-venda/:idFunc', (req, res) => {
    let idFunc = req.params.idFunc
    Vendas.altVendas(req.body.nomeFunc,req.body.valorVenda, idFunc, res);
    res.redirect('/cadastro-vendas');
});

app.get('/del-venda/:idFunc', (req, res) => {
    Vendas.delVendas(req.params.idFunc);
    res.redirect('/cadastro-vendas');
})

app.get('/limpar-tabela-vendas', (req, res) => {
    Vendas.limparVendas();
    res.redirect('/cadastro-vendas');
})

app.listen(8080);

