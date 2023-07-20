package com.projetos.login.controller;

import com.projetos.login.model.usuario.DadosAberturaLogin;
import com.projetos.login.model.usuario.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class LoginController {
    public static UsuarioService service = new UsuarioService();
    @GetMapping("/")
    public String pagIndex() {
        return "index";
    }

    @GetMapping("/login")
    //(@RequestParam(name = "funcionalidade", required = false) String dados, Model model)
    public String pagLogin(@RequestParam(name = "acao", required = false) String acao, Model model, String login) {
        if(login != null) {
            var usuario = service.buscarUsuarioPorLogin(login);
            model.addAttribute("usuario", usuario);
        }
        model.addAttribute("selAcao", acao);
        return "login";
    }

    @PostMapping("/altUsuario")
    public String altUsuario(DadosAberturaLogin dados) {
        service.alterarUsuario(dados);
        return "redirect:/listUsuario";
    }

    @PostMapping("/cadUsuario")
    public String cadUsuario(DadosAberturaLogin dadosUsuario) {
        service.abrirUsuario(dadosUsuario);
        return "redirect:/";
    }

    @GetMapping("/listUsuario")
    public String listUsuario(Model model) {
        var usuarios = service.listarUsuarios();
        System.out.println(usuarios);
        model.addAttribute("usuarios", usuarios);
        return "listUsuario";
    }

    @GetMapping("/delUsuario")
    public String delUsuario(DadosAberturaLogin dados) {
        service.encerrar(dados.login());
        return "redirect:/listUsuario";
    }


}
