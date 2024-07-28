package com.example.my_chat.controller;

import com.example.my_chat.domain.usuario.DadosAutentication;
import com.example.my_chat.domain.usuario.DadosUsuario;
import com.example.my_chat.domain.usuario.TipoUsuario;
import com.example.my_chat.domain.usuario.Usuario;
import com.example.my_chat.infra.security.DadosTokenJWT;
import com.example.my_chat.infra.security.TokenService;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = {"*"})
public class AutenticacaoController {
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutentication dados) {
        var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenticacao = manager.authenticate(autenticacaoToken);
        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("cad/user")
    public ResponseEntity cadFuncionario(@RequestBody @Valid DadosUsuario dados) {
        String senhaCodificada = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario usuario = new Usuario(dados.nome(), dados.login(), senhaCodificada, TipoUsuario.USER);
        repository.save(usuario);
        return ResponseEntity.ok().build();
    }
    @PostMapping("cad/admin")
    public ResponseEntity cadAdmin(@RequestBody @Valid DadosUsuario dados) {
        String senhaCodificada = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario usuario = new Usuario(dados.nome(), dados.login(), senhaCodificada, TipoUsuario.ADMIN);
        repository.save(usuario);
        return ResponseEntity.ok().build();
    }

}
