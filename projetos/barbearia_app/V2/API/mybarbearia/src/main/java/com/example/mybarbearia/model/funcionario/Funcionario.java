package com.example.mybarbearia.model.funcionario;

import com.example.mybarbearia.model.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "funcionario")
@Entity(name = "Funcionario")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    @Enumerated(EnumType.STRING)
    private Cargo cargo;
    @Embedded
    private Endereco endereco;
    private Integer porcentagemComissao;
    private Boolean ativo;

    public Funcionario(DadosCadastroFuncionario dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.cargo = dados.cargo();
        this.porcentagemComissao = dados.porcentagemComissao();
        this.endereco = new Endereco(dados.endereco());
        this.ativo = true;
    }

    public void atualizaInformacoes(DadosAtualizaFuncionario dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.email() != null) {
            this.email = dados.email();
        }
        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if(dados.cargo() != null) {
            this.cargo = dados.cargo();
        }
        if(dados.porcentagemComissao() != null) {
            this.porcentagemComissao = dados.porcentagemComissao();
        }
    }

    public void apagarLogico() {
        this.ativo = false;
    }
}
