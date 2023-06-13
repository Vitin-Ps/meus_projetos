package com.crud.cad_usuario.model.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public abstract class CrudDAO {
    public static Object consultarDesconto(EntityManager entityManager, String nomeFuncionario) {
        String sql = "SELECT desconto_func FROM funcionarios WHERE nome_func = ?";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, nomeFuncionario);
        Object result = query.getSingleResult();
        return result;
    }

    public static void mudarPorcentagem(EntityManager entityManager, int porcentagemDesconto) {
        String sql = "SET @porcentagemDesconto = ?";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        nativeQuery.setParameter(1, porcentagemDesconto);
        nativeQuery.executeUpdate();
    }

}
