package com.crud.cad_usuario.model.DAO;

import com.crud.cad_usuario.model.venda.SomaValores;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class CrudDAO {
    public static int consultarDesconto(EntityManager entityManager, String nomeFuncionario) {
        String sql = "SELECT desconto_func FROM funcionarios WHERE nome_func = ?"; // selecionando o desconto_func com base no nome
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, nomeFuncionario);
        Object result = query.getSingleResult(); // retorna o dado em formato de Objeto
        return result != null ? (int) result : 0; // aqui fazemos um casting explicito transfomando ele em int
        // é um operador ternario que retorna informações a base de condições
        // condição ? valor_se_verdadeiro : valor_se_falso;
    }

    public static void mudarPorcentagem(EntityManager entityManager, int porcentagemDesconto) {
        String sql = "SET @porcentagemDesconto = ?"; // a query que vai se enviada para o banco
        Query nativeQuery = entityManager.createNativeQuery(sql); // Query --> classe do jpa que permite execuções de querys no db, esse comando vai preparar a string que contém a query
        nativeQuery.setParameter(1, porcentagemDesconto); // vai mudar o ? pelo parametro int
        nativeQuery.executeUpdate(); // executa a query no banco de dados
    }

    public static Set<SomaValores> obterSomaVendasDescontos(EntityManager entityManager, String nomeFuncionario) {
        String sql = "SELECT funcionario, SUM(valor_venda), SUM(desconto_venda) FROM vendas WHERE funcionario = ? GROUP BY funcionario"; // seleciona a soma  dos valores com base no nome_func
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, nomeFuncionario);
        List<Object[]> resultList = query.getResultList();

        Set<SomaValores> resultSet = new HashSet<>();
        for (Object[] result : resultList) {
            String funcionario = (String) result[0];
            BigDecimal valorVenda = BigDecimal.valueOf((Double) result[1]);
            BigDecimal descontoVenda = BigDecimal.valueOf((Double) result[2]);

            SomaValores somaValores = new SomaValores(funcionario, valorVenda, descontoVenda);
            resultSet.add(somaValores);
        }

        System.out.println(resultSet);

        return resultSet;
    }

    public static Set<SomaValores> obterValoresFunc(EntityManager entityManager, String nomeFuncionario) {
        String sql = "SELECT funcionario, valor_venda, desconto_venda FROM vendas WHERE funcionario = ?"; // seleciona todos os valores e descontos pelo nome_func
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, nomeFuncionario);
        List<Object[]> resultList = query.getResultList(); // retorna o resultado numa classe de array (List)

        Set<SomaValores> resultSet = new HashSet<>(); // cria um array Set com base nas informações do record
        for (Object[] result : resultList) { // cria um Obejct para representar o set no foreach
            String funcionario = (String) result[0]; // cria variveis e guarda as informações nelas
            BigDecimal valorVenda = BigDecimal.valueOf((Double) result[1]); // pegar um valor decimal e guarda num Bigdecimal atravé de um casting
            BigDecimal descontoVenda = BigDecimal.valueOf((Double) result[2]);

            SomaValores somaValores = new SomaValores(funcionario, valorVenda, descontoVenda); // salva as informações das variveis no record
            resultSet.add(somaValores); // adiciona na lista Set
        }

        return resultSet; // retorna alista do tipo Set
    }


}
