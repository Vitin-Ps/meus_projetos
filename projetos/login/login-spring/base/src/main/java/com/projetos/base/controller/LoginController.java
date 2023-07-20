package com.projetos.base.controller;

import com.projetos.base.model.users.DadosCadastroUsers;
import com.projetos.base.model.users.Users;
import com.projetos.base.model.users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/")
    public String pagIndex() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Long idUsers) {
        if(idUsers != null) {
            var users = usersRepository.getReferenceById(idUsers);
            model.addAttribute("user", users);
        }
        return "login";
    }

    @GetMapping("/listUsers")
    public String listUsers(Model model, Long idUsers) {
        model.addAttribute("users", usersRepository.findAll());
        return "listUsers";
    }

    @PostMapping("/cadUsers")
    @Transactional
    public String cadUsers(DadosCadastroUsers dados) {
//        var users = new Users(dados);
//        usersRepository.save(users);
        return "redirect:/login";
    }

    @GetMapping("/delUsers")
    public String delUsers(Long idUsers) {
        usersRepository.deleteById(idUsers);
        return "redirect:/listUsers";
    }

    @PutMapping("/cadUsers")
    @Transactional
    public String altUsers(DadosCadastroUsers dados) {
        var users = usersRepository.getReferenceById(dados.Id());
//        users.atualizaDados(dados);
        return "redirect:/listUsers";
    }


}
