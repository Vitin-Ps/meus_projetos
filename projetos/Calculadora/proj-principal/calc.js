let text = document.getElementById("textInput");
let res = document.getElementById("res");
let limpar = document.getElementById("limpar");
let botoes = document.querySelectorAll('button[name="botoes"]');
let btnRes = document.getElementById("btnRes");
let btnOperacoes = document.querySelectorAll('button[name="btnOperacoes"]')
let resPrimitivo = document.getElementById("resPrimitivo")

let valorAnterior = [];
let valorTotal = 0;
let valor = 0;
let btnAnterior = "";
let btnIgual = false;
let btnCE = "CE";
let tipoOperacao = "";
let valor01 = 0;

text.value = "0";

text.addEventListener("input", () => {
    let txtInput = Number(text.value);
    if(txtInput !== 0) {
      btnRes.innerHTML = "C";
      btnCE = "C";
    } else {
      btnRes.innerHTML = "CE";
      btnCE = "CE";
    }
  })


botoes.forEach(botao => {
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

function funForEach(btnVar) {
    btnVar.forEach(btn => {
        CorPadrao(btn);
    })
}
//função das operações matemáticas
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
    valorAnterior.push(valorTotal);

    res.innerHTML += `<div><p>${valor01} ${tipoOperacao}  ${valor}</p><p>=</p><p>${valorTotal}</p></div>`;

    resPrimitivo.innerHTML = `<div><p>${valor01} ${tipoOperacao}  ${valor}</p><p>=</p><p>${valorTotal}</p></div>`;

}



function apertar(botao) {
    CorEscura(botao);// está usando o this que referencia ele mesmo
}

function soltar(botao) {
    CorPadrao(botao);
}

function mostrarNum(numero) {
    CorPadrao(btnRes);
    if (text.value == "0"){
        text.value = `${numero}`;
        text.focus();
    } else {
        text.value += `${numero}`;
        text.focus();
    }
}


function operacoes(operacao) {
    if(valorTotal == 0) {
        valorTotal = Number(text.value);
        valor = 1;
        valor01 = valorTotal;
    } else if (btnIgual == true){
        valor = Number(text.value);
        valor01 = valorTotal;

    } else {
        valor = Number(text.value);
        valor01 = valorAnterior
        calculo(btnAnterior);
    }
        tipoOperacao = operacao;
        valor01 = valorTotal;
        btnAnterior = operacao;
        btnIgual = false;
        text.value = "0";
    }

function apertarOperacoes(botao) {
    funForEach(btnOperacoes);
    CorClara(botao);
}


function btnFuncional(func) {
    switch (func) {
        case 'limpar':
            switch(btnCE) {
                case 'C':
                    text.value = "0";
                    btnRes.innerHTML = "CE";
                    btnCE = "CE";

                    CorClara(btnRes);
                    break;
                case 'CE':
                    valorAnterior = [];
                    valorTotal = 0;
                    valor = 0;
                    btnAnterior = "";
                    btnIgual = false;
                    text.value = "0";
                    res.innerHTML = "";
                    resPrimitivo.innerHTML = "";

                    funForEach(btnOperacoes);
                    CorPadrao(btnRes);
                    break;
                default:
                    window.alert("ERRO!!! Selecione Algum botão.")
            }
            break;
        case 'igual':
            if (text.value !== "0" && btnIgual == false) {
                valor = Number(text.value);
                btnIgual = true;
            } else {
                btnOperacao = btnAnterior;
            }
            valor01 = valorTotal;
            calculo(btnAnterior);
            text.value = `${valorTotal}`;
            break;
        default:
    }
}

function funInput() {
    text.value = "0";
    res.innerHTML = "";
}
