package com.crud.cad_usuario.controller;

import com.crud.cad_usuario.model.funcionario.DadosCadastroFuncionario;
import com.crud.cad_usuario.model.funcionario.Funcionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // é uma anotação usada para marcar uma classe como um controlador no contexto do Spring MVC. Essa anotação indica que a classe é responsável por lidar com as requisições HTTP e retornar as respostas correspondentes.
@RequestMapping // usada para mapear uma requisição HTTP para um método específico dentro de um controlador.
public class CrudController {

    @Autowired
    private FuncionarioRepository repository;

    @GetMapping("/")
    public String paginaPricipal(){
        return "index";
    }

    @GetMapping("/funcionarios")
    public String paginaFunconarios(){
        return "subPage/funcionarios";
    }

    @PostMapping("/cadastrar")
    @Transactional
    public String cadFuncionario(DadosCadastroFuncionario dados) {
        var funcionario = new Funcionario(dados);
        repository.save(funcionario);
        return "redirect/funcionarios";
    }

}
