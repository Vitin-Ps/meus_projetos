package com.example.my_chat.controller;

import com.example.my_chat.domain.usuario.DadosDetalhamentoUser;
import com.example.my_chat.domain.usuario.Usuario;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity DetalharUser(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new DadosDetalhamentoUser(usuario));
    }

    @GetMapping
    public ResponseEntity listar() {
        var listaUser = usuarioRepository.findAll().stream().map(DadosDetalhamentoUser::new).toList();
        return ResponseEntity.ok(listaUser);
    }
}
