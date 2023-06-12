package com.crud.cad_usuario.model.funcionario;


import jakarta.persistence.*;

@Entity // usado para o JPA reconhecer essa classe e mapela para o baanco de dados
@Table(name="funcionarios")// indica qual é a tabela
public class Funcionario {

    @Id // indica que esse atributo é relacionado a chave Primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // indica que a chave é auto_increment
    private long idFunc;
    private String nomeFunc;
    private String matricula;
    private String numero;
    private String email;
    private int descontoFunc;

    public Funcionario (DadosCadastroFuncionario dados) { // vai usar um record, que é feito especiamente para esses cados de pegar as infomações dos formularios e fazerem toda a conversão para o objeto
        this.nomeFunc = dados.nomeFunc();
        this.matricula = dados.matricula();
        this.numero = dados.numero();
        this.email = dados.email();
        this.descontoFunc = dados.descontoFunc();
    }

    public Funcionario () {} // é importante ter esse contrutor padrão, se não vai dar erro mais na frente

    public long getIdFunc() {
        return idFunc;
    }

    public String getNomeFunc() {
        return nomeFunc;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNumero() {
        return numero;
    }

    public String getEmail() {
        return email;
    }

    public int getDescontoFunc() {
        return descontoFunc;
    }

    @Override
    public String toString() { // retorna uma string com as informções de cada atributo
        return "Funcionario{" +
                "idFunc=" + idFunc +
                ", nomeFunc='" + nomeFunc + '\'' +
                ", matricula='" + matricula + '\'' +
                ", numero='" + numero + '\'' +
                ", email='" + email + '\'' +
                ", descontoFunc=" + descontoFunc +
                '}';
    }

    public void atualizaDados(DadosAlteracaoFuncionario dados) {
        this.nomeFunc = dados.nomeFunc();
        this.matricula = dados.matricula();
        this.numero = dados.numero();
        this.email = dados.email();
        this.descontoFunc = dados.descontoFunc();
    }
}
