let text = document.getElementById("textInput");
let res = document.getElementById("res");

let valorAnterior = [];
let valorTotal = 0;
let valor = 0;
let btnAnterior;

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
    valor = Number(text.value);
    valorTotal = valor;
    valor = 0;
    text.value = "";

    btnAnterior = operacao;
    text.focus();
}

function btnFuncional(func) {
    switch (func) {
        case 'limpar':
            valorTotal = [];
            valorAnterior = 0;
            valor = 0;
            btnAnterior = "";
            text.value = "";
            res.innerHTML = "";
            break;
        case 'igual':
            switch (btnAnterior) {
                case 'soma':
                    valor = Number(text.value);
                    valorAnterior = Number(valorAnterior);
                    valorAnterior = valorAnterior + valor;
                    break;
                case 'subtracao': 
                    valor = Number(text.value);
                    valorAnterior = Number(valorAnterior);
                    valorAnterior = valorAnterior - valor;
                    break;
                case 'divisao':
                    valor = Number(text.value);
                    valorAnterior = Number(valorAnterior);
                    valorAnterior = valorAnterior / valor;
                    break;
                case 'multiplicacao':
                    valor = Number(text.value);
                    valorAnterior = Number(valorAnterior);
                    valorAnterior = valorAnterior * valor;
                    break;
                default:
                    window.alert("Erro!!! Clique em uma Operação válida");
            }
            text.value = `${valorAnterior}`;
            valorTotal.push(valorAnterior);
            text.value = `${valorAnterior}`;
            res.innerHTML = `<ul>`
            for (i = 0; i < valorTotal.length; i++) {
                res.innerHTML += `<li>${valorTotal[i]}</li>`;
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

