package com.crud.cad_usuario.model.funcionario;

import com.crud.cad_usuario.model.funcionario.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {} // vai estender a uma classe do jpa, Funcionario- é o objeto representado pela tabela, e o Long é o tipo da chave primária

