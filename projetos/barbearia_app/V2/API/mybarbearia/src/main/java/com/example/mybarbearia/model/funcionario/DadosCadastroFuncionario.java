package com.example.mybarbearia.model.funcionario;

import com.example.mybarbearia.model.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroFuncionario(
        @NotBlank(message = "Nome {dados.obrigatorio}")
        String nome,
        @NotBlank(message = "E-mail {dados.obrigatorio}")
        String email,
        @NotBlank(message = "CPF {dados.obrigatorio}")
        @Pattern(regexp = "\\d{11}")
        String cpf,
        @NotBlank(message = "Telefone {dados.obrigatorio}")
        String telefone,
        @NotNull(message = "Cargo {dados.obrigatorio}")
        Cargo cargo,
        @NotNull(message = "Porcentagem {dados.obrigatorio}")
        Integer porcentagemComissao,
        @NotNull(message = "Endereço {dados.obrigatorio}")
        @Valid
        DadosEndereco endereco
) {
}
