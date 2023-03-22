let text = document.getElementById("textInput");
let res = document.getElementById("res");
let limpar = document.getElementById("limpar");


let valorAnterior = [];
let valorTotal = 0;
let valor = 0;
let btnAnterior = "";
let btnIgual = false;



//função das operações matemáticas
function calculo(valorCalc) {
    switch (valorCalc) {
        case 'soma': 
            valorTotal += valor;
            break;
        case 'multiplicacao':
            valorTotal = valorTotal * valor;
            break;
        case 'subtracao':
            valorTotal -= valor;
            break;
        case 'divisao':
            valorTotal = valorTotal / valor;
            break;
        case 'porcentagem':
            valorTotal = (valorTotal / 100) * valor;
        default:
            window.alert("Erro")
    }
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
    if(valorTotal == 0) {
        valorTotal = Number(text.value);
        valor = 1;
        res.innerHTML = `${valorTotal} ${valor}`;
    } else {
        valor = Number(text.value);
        calculo(btnAnterior);
        }
        btnAnterior = operacao;
        btnIgual = false;
        text.value = "";
        res.innerHTML = `${valorTotal} ${valor}`;
        
    }


function btnFuncional(func) {
    switch (func) {
        case 'limparTotal':
            valorAnterior = [];
            valorTotal = 0;
            valor = 0;
            btnAnterior = "";
            text.value = "";
            res.innerHTML = "";
            break;
        case 'limparParcial':
            text.value = "";
            break;
        case 'igual':
            if (text.value !== "" && btnIgual == false) {
                valor = Number(text.value);
                btnIgual = true;
            }
            calculo(btnAnterior);
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
