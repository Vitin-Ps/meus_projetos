package com.projetos.base.controller;

import com.projetos.base.model.user.AlteracaoUser;
import com.projetos.base.model.user.AuthenticationUser;
import com.projetos.base.model.user.CadastroUser;
import com.projetos.base.model.user.User;
import com.projetos.base.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;

    // ------------------ CRUD --------------------

    @GetMapping
    public String login(@RequestParam(name = "acao", required = false) String acao, Model model, String login) {
//        if(id != null) {
//            var user = repository.getReferenceByLogin(login);
//            model.addAttribute("user", user);
//        }
        model.addAttribute("selAcao", acao);
        return "login";
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model) {
        model.addAttribute("users", repository.findAll());
        return "listUsers";
    }

    @PostMapping("/cadUsers")
    @Transactional
    public String cadUsers(@Valid CadastroUser data) {
        if(this.repository.findByLogin(data.login()) != null) {
            System.out.println("Usuario já registrado");
            return "redirect:/";
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        System.out.println(newUser.getRole());
        this.repository.save(newUser);

        return "redirect:/";
    }

    @GetMapping("/delUsers")
    public String delUsers(Long id) {
        repository.deleteById(id);
        return "redirect:/listUsers";
    }
//
//    @PutMapping("/cadUsers")
//    @Transactional
//    public String altUsers(AlteracaoUser dados) {
//        var users = repository.getReferenceById(dados.id());
//        users.atualizaDados(dados);
//        return "redirect:/";
//    }

    // ---------- Authentication -----------------

    @PostMapping("/login")
    public String login(AuthenticationUser data) {
        System.out.println("entrou");
        System.out.println(data.login() + data.password());
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        System.out.println("passou01");
        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("passou02");
        return "redirect:/";
    }
}
