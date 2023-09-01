package com.example.mybarbearia.model.estoque;

import com.example.mybarbearia.model.produto.Produto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "estoque")
@Entity(name = "Estoque")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto")
    private Produto produto;
    private Integer quantidade;

    public void alterarQuantidade(DadosAtualizaEstoque dados) {
        if(dados.quantidade() != null) {
            if(dados.alterarQuantidade() == AlterarQuantidade.ADICIONAR) {
                this.quantidade += dados.quantidade();
            } else {
                this.quantidade -= dados.quantidade();
            }
        }
    }
}
