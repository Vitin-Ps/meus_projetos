package com.crud.cad_usuario.model.funcionario;

public record DadosAlteracaoFuncionario(Long idFunc, String nomeFunc, String matricula, String numero, String email, int descontoFunc) {
} // a mesama coisa que cadastro, mas também vai pegar as informações do id
