package com.example.mybarbearia.model.funcionario;

public record DadosListagemFuncionario(
        Long id,
        String nome,
        String email,
        String cpf,
        String telefone
) {
    public DadosListagemFuncionario(Funcionario funcionario) {
        this(funcionario.getId(), funcionario.getNome(), funcionario.getEmail(), funcionario.getCpf(), funcionario.getTelefone());
    }
}
