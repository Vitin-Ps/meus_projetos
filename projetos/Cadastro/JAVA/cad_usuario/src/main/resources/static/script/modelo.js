function checkInput() {
  let inputs = document.querySelectorAll('.inputs');

  for (let i = 0; i < inputs.length; i++) {
      if (inputs[i].value === "") {
          window.alert("Preencha todos os Dados!!!");
          event.preventDefault();
          return false; // Impede o envio do formulário
      }
  }

  return true; // Permite o envio do formulário
}


function checkMudancas() {
  if (window.confirm("Deseja Continuar?")) {    
  } else {
    event.preventDefault();
  }
}


