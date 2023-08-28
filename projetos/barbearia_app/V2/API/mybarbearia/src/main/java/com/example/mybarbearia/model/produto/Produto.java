package com.example.mybarbearia.model.produto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name= "produto")
@Entity(name= "Produto")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private String marca;
    @Enumerated(EnumType.STRING)
    private TipoProduto tipo;
    private BigDecimal comissao;
    private Boolean ativo;

    public Produto(DadosCadastroProduto dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.marca = dados.marca();
        this.tipo = dados.tipo();
        this.comissao = dados.comissao();
    }

    public void atualizarInformacoes(DadosAtualizaProduto dados) {
        if(dados.nome() != null) {
            this.nome = dados.nome();
        }
        if(dados.descricao() != null) {
            this.descricao = dados.descricao();
        }
        if(dados.preco() != null) {
            this.preco = dados.preco();
        }
        if(dados.marca() != null) {
            this.marca = dados.marca();
        }
        if(dados.tipo() != null) {
            this.tipo = dados.tipo();
        }
        if(dados.comissao() != null) {
            this.comissao = dados.comissao();
        }
    }

    public void excluirLogico() {
        this.ativo = false;
    }
}
