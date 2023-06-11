let nomeFunc = document.getElementById("nomeFunc");
let valorVenda = document.getElementById("valorVenda");


        function apagar() { // botão apagar
            if(confirm("Tem certeza que quer Apagar esse dado???")) {
                window.alert("Dado apagado!!!");
                console.log("Dado apagado");
            }else {
                console.log("Dado não apagado!!!");
                event.preventDefault();
            }

        }

        function adicionar(){ // botão adicionar
            if(nomeFunc.value === "" || valorVenda.value === "" || isNaN(valorVenda.value)) {
                window.alert("ERRO!!! Preencha os dados corretamente");
                event.preventDefault();
            } else {
                window.alert("Dado Adicionado!!!");
            }
        }

        function limpar() { // botão limpar
            if(confirm("Tem certeza que você quer apagar todos os dados da Tabela!!!")) {
                window.alert("Dados da tabela apagados!!!");
                console.log("Dados da tabela apagados");
            } else {
                console.log("Dados da tabela não apagados");
                event.preventDefault();
            }
        }
        
        
