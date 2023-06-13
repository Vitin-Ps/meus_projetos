package com.crud.cad_usuario.model.venda;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity // usado para o JPA reconhecer essa classe e mapela para o baanco de dados
@Table(name="vendas")// indica qual é a tabela
public class Venda {

    @Id // indica que esse atributo é relacionado a chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que a chave é auto_increment
    private long idVenda;
    private String funcionario;
    private BigDecimal valorVenda;
    private BigDecimal descontoVenda;

    public Venda (DadosCadastroVenda dados) { // vai usar um record, que é feito especiamente para esses cados de pegar as infomações dos formularios e fazerem toda a conversão para o objeto
        this.funcionario = dados.funcionario();
        this.valorVenda = dados.valorVenda();
    }

    public Venda () {} // é importante ter esse contrutor padrão, se não vai dar erro mais na frente

    public long getIdVenda() {
        return idVenda;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public BigDecimal getDescontoVenda() {
        return descontoVenda;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "idVenda=" + idVenda +
                ", funcionario='" + funcionario + '\'' +
                ", valorVenda=" + valorVenda +
                ", descontoVenda=" + descontoVenda +
                '}';
    }

    public void atualizaDados(DadosAlteracaoVenda dados) {
        this.funcionario = dados.funcionario();
        this.valorVenda = dados.valorVenda();
        this.descontoVenda = dados.descontoVenda();
    }
}
