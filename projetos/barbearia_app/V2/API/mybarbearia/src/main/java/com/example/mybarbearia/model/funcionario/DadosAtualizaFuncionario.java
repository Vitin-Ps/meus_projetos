package com.example.mybarbearia.model.funcionario;

public record DadosAtualizaFuncionario(
        Long id,
        String nome,
        String email,
        String telefone,
        Cargo cargo,
        Integer porcentagemComissao
) {
}
