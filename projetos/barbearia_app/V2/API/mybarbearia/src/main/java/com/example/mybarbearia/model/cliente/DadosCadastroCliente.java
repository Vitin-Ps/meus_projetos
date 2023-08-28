package com.example.mybarbearia.model.cliente;

import com.example.mybarbearia.model.endereco.DadosEndereco;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroCliente(
        @NotBlank(message = "Nome {dados.obrigatorio}")
        String nome,
        @NotBlank(message = "E-mail {dados.obrigatorio}")
        String email,
        @NotBlank(message = "Telefone {dados.obrigatorio}")
        String telefone,
        @NotNull(message = "Endereço {dados.obrigatorio}")
        @Valid
        DadosEndereco endereco
) {
}
