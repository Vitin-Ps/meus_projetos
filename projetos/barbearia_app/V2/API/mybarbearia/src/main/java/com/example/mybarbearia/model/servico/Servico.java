package com.example.mybarbearia.model.servico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "servico")
@Entity(name = "Servico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String duracao;
    private Boolean ativo;

    public Servico(DadosCadastroServico dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.duracao = dados.duracao();
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizaServico dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if(dados.preco() != null) {
            this.preco = dados.preco();
        }
        if(dados.duracao() != null) {
            this.duracao = dados.duracao();
        }

    }

    public void apagarLogico() {
        this.ativo = false;
    }
}
