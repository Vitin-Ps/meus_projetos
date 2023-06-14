package com.crud.cad_usuario.controller;

import com.crud.cad_usuario.model.DAO.CrudDAO;
import com.crud.cad_usuario.model.funcionario.DadosAlteracaoFuncionario;
import com.crud.cad_usuario.model.funcionario.DadosCadastroFuncionario;
import com.crud.cad_usuario.model.funcionario.Funcionario;
import com.crud.cad_usuario.model.funcionario.FuncionarioRepository;
import com.crud.cad_usuario.model.venda.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ognl.ObjectElementsAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller // é uma anotação usada para marcar uma classe como um controlador no contexto do Spring MVC. Essa anotação indica que a classe é responsável por lidar com as requisições HTTP e retornar as respostas correspondentes.
@RequestMapping // usada para mapear uma requisição HTTP para um método específico dentro de um controlador.
public class CrudController {

    @Autowired // Ele permite que você injete instâncias de classes em outras classes, evitando a necessidade de criar manualmente as instâncias.
    private FuncionarioRepository repositoryFunc; // vai buscar a interface e usala como atributo
    @Autowired
    private VendaRepository repositoryVenda;
    @Autowired
    private EntityManager entityManager; // classe do Jpa usada para mapear objetos para tabelas do banco de dados




    @GetMapping("/")
    public String paginaPricipal(){
        return "index";
    }




    // --------------------------- FUNCIONARIO ------------------------------------------

    @GetMapping("/funcionarios") // criando um link --> localhost:8080/funcionarios
    public String paginaFuncionarios(Model model,  Long idFunc){ // pega o id pelo link
        if(idFunc != null) { // se o id for diferente de null, no cado da alteração
            var funcionario = repositoryFunc.getReferenceById(idFunc); // ele busca as informações por id
            model.addAttribute("funcionario", funcionario); // joga essas inormações numa variavel web
        }
        this.listaModelFunc(model, "funcionarios");  // chama o método para enviar as informações para variavel web
        return "subPage/funcionarios";
    }

    @PostMapping("/cadFunc") // mapeia uma requisição http do tipo Post
    @Transactional // garante que a operação de salvar o funcionário no banco de dados seja realizada dentro de uma transação.
    public String cadFuncionario(DadosCadastroFuncionario dados) { // vai receber as infromações via post e mandalas para o record
        var funcionarios = new Funcionario(dados); // vai usar o construtor que muda os dados, usando os dados do record
        repositoryFunc.save(funcionarios); // vai salvar as informações do objeto em um método do jpa que deve ser um insert into e executalo no banco
        return "redirect:/funcionarios"; // redireciona a página
    }

    @GetMapping("/delFunc")
    public String delFuncionarios (Long idFunc) { // vai pegar a infrormação que estiver depois do parâmetro idFunc
        repositoryFunc.deleteById(idFunc); // deletar por id
        return "redirect:/funcionarios";
    }

    @PutMapping("/cadFunc") // tem que ter o mesmo destino que o de cadastrar
    @Transactional
    public String altFuncionario (DadosAlteracaoFuncionario dados) {// vai pegar as informações do record alterarDados
        var funcionario = repositoryFunc.getReferenceById(dados.idFunc()); // vai pegar as informações da tabela baseado no id
        funcionario.atualizaDados(dados); // usa um método jpa para atualizar os dados
        return "redirect:/funcionarios";
    }

    private void listaModelFunc(Model model, String atributoWeb) { // a classe Model é usada para transportar as informações do banco para um array que poderá ser mostrado na web
        model.addAttribute(atributoWeb, repositoryFunc.findAll()); // vai levar para a variável atributoWeb no html as informações do objeto que no caso é um método da jpa, que deve usar um SELECT *
    }

    // ------------------------------ VENDAS --------------------------------------

    @GetMapping("/vendas") // criando um link --> localhost:8080/vendas
    public String paginaVendas(Model model,  Long idVenda){ // pega o id pelo link

        if(idVenda != null) { // se o id for diferente de null, no cado da alteração
            var venda = repositoryVenda.getReferenceById(idVenda); // ele busca as informações por id
            model.addAttribute("venda", venda); // joga essas inormações numa variavel web
        }
        this.listaModelFunc(model,"funcionarios"); // para o select dos nomes dos funcionarios
        this.listaModelVenda(model, "vendas");  // chama o método para enviar as informações para variavel web
        return "subPage/vendas";
    }

    @PostMapping("/cadVenda") // mapeia uma requisição http do tipo Post
    @Transactional // garante que a operação de salvar o funcionário no banco de dados seja realizada dentro de uma transação.
    public String cadVenda(DadosCadastroVenda dados) { // vai receber as infromações via post e mandalas para o record
        int desconto = CrudDAO.consultarDesconto(entityManager, dados.funcionario()); // aqui buscamos o desconto_func
        CrudDAO.mudarPorcentagem(entityManager, desconto); // aqui mudamos a porcentagem
        var vendas = new Venda(dados); // vai usar o construtor que muda os dados, usando os dados do record
        repositoryVenda.save(vendas); // vai salvar as informações do objeto em um método do jpa que deve ser um insert into e executalo no banco
        return "redirect:/vendas"; // redireciona a página
    }

    @GetMapping("/delVenda")
    public String delVenda (Long idVenda) { // vai pegar a infrormação que estiver depois do parâmetro idFunc
        repositoryVenda.deleteById(idVenda); // deletar por id
        return "redirect:/vendas";
    }

    @PutMapping("/cadVenda") // tem que ter o mesmo destino que o de cadastrar
    @Transactional
    public String altVenda (DadosAlteracaoVenda dados) {// vai pegar as informações do record alterarDados
        var venda = repositoryVenda.getReferenceById(dados.idVenda()); // vai pegar as informações da tabela baseado no id
        int desconto = CrudDAO.consultarDesconto(entityManager, dados.funcionario());
        CrudDAO.mudarPorcentagem(entityManager, desconto);
        venda.atualizaDados(dados); // usa um método jpa para atualizar os dados
        return "redirect:/vendas";
    }

    private void listaModelVenda(Model model, String atributoWeb) { // a classe Model é usada para transportar as informações do banco para um array que poderá ser mostrado na web
        model.addAttribute(atributoWeb, repositoryVenda.findAll()); // vai levar para a variável atributoWeb no html as informações do objeto que no caso é um método da jpa, que deve usar um SELECT *
    }

    // ------------------------ SOMAVALORES --------------------------

    @GetMapping("/somaValores")
    @Transactional
    public String paginaValores(Model model, String func) { // recebe a varivel do post pelo link --> obs; tem que se o mesmo nome que está no link
        this.listaModelFunc(model, "funcionarios"); // retorna os nome dos funcionarios no formulario
        Set<SomaValores> listaValores = CrudDAO.obterValoresFunc(entityManager, func); // obtem valores do funcionario
        Set<SomaValores> soma = CrudDAO.obterSomaVendasDescontos(entityManager, func);
        model.addAttribute("vendas", listaValores); // mostra os valores na lista, com a varivel vendas para web
        model.addAttribute("somaValores", soma); // mostra a oma atravez da variavel somaValores na web
        return "subPage/somaValores";
    }

    @PostMapping("/buscaFunc")
    @Transactional
    public String buscaFuncionario(String funcionario) { // ao clicar em enviar ele recebe a fincomação do input e guarda na string funcionario
        return "redirect:/somaValores?func=" + funcionario; // envia a variavel funcionario pelo get
    }
    // ao clicar em enviar ele recebe a fincomação do input e guarda na string funcionario


}
