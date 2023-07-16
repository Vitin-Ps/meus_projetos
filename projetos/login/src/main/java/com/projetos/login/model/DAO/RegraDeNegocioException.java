package com.projetos.login.model.DAO;

public class RegraDeNegocioException extends RuntimeException { // uma exceção
    public RegraDeNegocioException(String mensagem) {
        super(mensagem); // método do Runtime exepetion
    }
}
