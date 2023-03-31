let text = document.getElementById("textInput");
let res = document.getElementById("res");
let limpar = document.getElementById("limpar");
let botoes = document.querySelectorAll('button[name="botoes"]');
let btnRes = document.getElementById("btnRes");
let btnOperacoes = document.querySelectorAll('button[name="btnOperacoes"]')
let resPrimitivo = document.getElementById("resPrimitivo")

let valorTotal = 0;
let valor = 0;
let btnAnterior = "";
let btnIgual = false;
let btnCE = "CE";
let tipoOperacao = "";
let valor01 = 0;

text.value = "0"; // adiciona 0 ao input

// -------------- funções do botão CE -------------------

// ao digitar no input, ele muda o botão
text.addEventListener("input", () => {
    let txtInput = Number(text.value);
    if(txtInput !== 0) { // se o conteúdo do input for diferente de 0
      btnRes.innerHTML = "C";
      btnCE = "C";
    } else {
      btnRes.innerHTML = "CE";
      btnCE = "CE";
    }
  })

// mesma função, mas é usafda quando se aciona o click dos botões 
// obs: foi usado querry all para cria uma variavel para todos os botões
botoes.forEach(botao => { // usado para passar por todos os botões, funciona como um array e seus indices
    botao.addEventListener("click", () => {
    let txtInput = Number(text.value);
    if(txtInput !== 0) {
      btnRes.innerHTML = "C";
      btnCE = "C";
    } else {
      btnRes.innerHTML = "CE";
      btnCE = "CE";
    }
  })
  })
// ----------------- cores dos botões ---------------------

function CorClara(btnVar) { 
    btnVar.style.backgroundColor = "var(--cor07)";
    btnVar.style.borderColor = "var(--cor01)"; 
    btnVar.style.color = "var(--cor5)";
}

function CorPadrao(btnVar) {
    btnVar.style.backgroundColor = "var(--cor01)";
    btnVar.style.borderColor = "var(--cor05)";
    btnVar.style.color = "var(--cor06)";
}

function CorEscura(btnVar) {
    btnVar.style.backgroundColor = "var(--cor05)";
    btnVar.style.borderColor = "var(--cor01)";
    btnVar.style.color = "var(--cor06)";
}

function funForEach(btnVar) { // função usada pra trazer a cor original das operções que tambem estão usando querry selector all
    btnVar.forEach(btn => {
        CorPadrao(btn);
    })
}
//----------------- operações matemáticas ----------------------

function calculo(valorCalc) {
    
    switch (valorCalc) {
        case '+':
            valorTotal += valor;
            break;
        case 'x':
            valorTotal = valorTotal * valor;
            break;
        case '-':
            valorTotal -= valor;
            break;
        case '/':
            valorTotal = valorTotal / valor;
            break;
        case '%':
            valorTotal = (valorTotal / 100) * valor;
            break;
        default:
            window.alert("Erro!!! Erro das Operações.")
    }
    res.innerHTML += `<div><p>${valor01} ${tipoOperacao}  ${valor}</p><p>=</p><p>${valorTotal}</p></div>`; 

    resPrimitivo.innerHTML = `<div><p>${valor01} ${tipoOperacao}  ${valor}</p><p>=</p><p>${valorTotal}</p></div>`;

}

// -------------- funcionalidades dos botões --------------------

function apertar(botao) { // função usada nos botões para mudar a cor, usando o onmousedown
    CorEscura(botao);// está usando o this que referencia ele mesmo
}

function soltar(botao) { // mesma coisa usando o onmouseup
    CorPadrao(botao);
}

function mostrarNum(numero) { // ao clicar no botão, mostra seu número correspondente
    if (text.value == "0"){ // se for 0 ele substituira o 0 pelo numero apertado
        text.value = `${numero}`;
        text.focus(); // da foco ao input
    } else {
        text.value += `${numero}`;
        text.focus();
    }
}


function operacoes(operacao) { // botões de operação
    if(valorTotal == 0) { // se o valorTotal for igual a 0
        valorTotal = Number(text.value);
        valor = 1;
        valor01 = valorTotal;
    } else if (btnIgual == true){ // usado para quando se apertar o igual e depois uma operação, não realizar a função calculo 2 vezez
        valor = Number(text.value);
        valor01 = valorTotal;

    } else {
        valor = Number(text.value);
        valor01 = valorTotal
        calculo(btnAnterior);
    }
        tipoOperacao = operacao; // varivel recebe o tipo da operação para mostrar no res
        valor01 = valorTotal;
        btnAnterior = operacao;
        btnIgual = false;
        text.value = "0";
        text.focus();
    }

function apertarOperacoes(botao) { // função para muda cor das operações
    funForEach(btnOperacoes);
    CorClara(botao);
}


function btnFuncional(func) { // botões funcionais da calculadora
    switch (func) {
        case 'limpar':
            switch(btnCE) {
                case 'C': // limpa somente o input
                    text.value = "0";
                    btnRes.innerHTML = "CE";
                    btnCE = "CE";

                    CorClara(btnRes);
                    break;
                case 'CE': //zera a calcularora
                    valorTotal = 0;
                    valor = 0;
                    btnAnterior = "";
                    btnIgual = false;
                    text.value = "0";
                    res.innerHTML = "";
                    resPrimitivo.innerHTML = "";

                    funForEach(btnOperacoes);
                    break;
                default:
                    window.alert("ERRO!!! Selecione Algum botão.")
            }
            break;
        case 'igual':
            if (text.value !== "0" && btnIgual == false) { // caso tenha sido uma opração antes do igual
                valor = Number(text.value);
                btnIgual = true;
            } else {
                tipoOperacao = btnAnterior; // salva o tipo ra operação para o res
            }
            valor01 = valorTotal; //salva o valor 1 quando o igual é aoertado
            calculo(btnAnterior);
            text.value = `${valorTotal}`; // mostra o resultado no input
            break;
        default:
    }
}

function funInput() { // quando se clica no inout ele zera 
    text.value = "";
    res.innerHTML = "";
    resPrimitivo.innerHTML = "";
}
