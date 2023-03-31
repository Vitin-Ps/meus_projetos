let text = document.getElementById("textInput");
let res = document.getElementById("res");

let valorAnterior = [];
let valorTotal = 0;
let valor = 0;
let btnAnterior = "";
let btnIgual = false;

//função das operações matemáticas
function calcular(valorOp) {
    switch (valorOp) {
        case 'soma':
            valorTotal += valor;
            break;
        case 'subtracao': 
            valorTotal -= valor;
            break;
        case 'divisao':
            valorTotal = valorTotal / valor;
            break;
        case 'multiplicacao':
            valorTotal = valorTotal * valor;
            break;
        default:
            window.alert("Erro!!! Clique em uma Operação válida");
    }
    valorAnterior.push(valorTotal);
}

function apertar(botao) {
    botao.style.backgroundColor = "var(--cor05)";
    botao.style.borderColor = "var(--cor01)"; // está usando o this que referencia ele mesmo
}

function soltar(botao) {
    botao.style.backgroundColor = "var(--cor01)";
    botao.style.borderColor = "var(--cor05)"; // está usando o this que referencia ele mesmo
}

function mostrarNum(numero) {
    text.value += `${numero}`;
}


function operacoes(operacao) {
    if (text.value == "") {
            
    } else {
        if(valorTotal == 0) {
        valorTotal = Number(text.value);
        } else {
            valor = Number(text.value);
        }
        calcular(operacao);
        btnAnterior = operacao;
        btnIgual = false;
        text.value = "";
        
    }
}

function btnFuncional(func) {
    switch (func) {
        case 'limpar':
            valorAnterior = [];
            valorTotal = 0;
            valor = 0;
            btnAnterior = "";
            text.value = "";
            res.innerHTML = "";
            break;
        case 'igual':
            if (text.value !== "" && btnIgual == false) {
                valor = Number(text.value);
                btnIgual = true;
            }
            calcular(btnAnterior);
            text.value = `${valorTotal}`;
            res.innerHTML = `<ul>`
            for (i = 0; i < valorTotal.length; i++) {
                res.innerHTML += `<li>${valorAnterior[i]}</li>`;
            }
            break;
        default:
            window.alert("Erro!!! Clique em Algum Botão da Calculadora.")
    }
}

function funInput() {
    text.value = "";
    res.innerHTML = "";
}

