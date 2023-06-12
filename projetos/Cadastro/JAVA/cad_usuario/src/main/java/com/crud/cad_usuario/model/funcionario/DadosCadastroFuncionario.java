package com.crud.cad_usuario.model.funcionario;

public record DadosCadastroFuncionario(String nomeFunc, String matricula, String numero, String email, int descontoFunc) {
} // vai pegar as informações dos inputs e pode ser usado quando quiser, emqualquer classe. obs: o nome das variáveis tem que ser os mesmos do input
