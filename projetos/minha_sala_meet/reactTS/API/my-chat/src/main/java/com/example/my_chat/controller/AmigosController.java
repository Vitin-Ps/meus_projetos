package com.example.my_chat.controller;

import com.example.my_chat.domain.amigo.AmigoRespository;
import com.example.my_chat.domain.amigo.AmigoService;
import com.example.my_chat.domain.amigo.DadosDetalhaAmigo;
import com.example.my_chat.domain.amigo.DadosInfoAmigo;
import com.example.my_chat.domain.solicitacao.DadosDetalhaSolicitacao;
import com.example.my_chat.domain.solicitacao.DadosRegistroSolicitacao;
import com.example.my_chat.domain.solicitacao.Solicitacao;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.SolicitacaoRepository;
import com.example.my_chat.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@CrossOrigin(origins = {"*"})
public class AmigosController {
    @Autowired
    private SolicitacaoRepository solicitacaoRepository;

    @Autowired
    private AmigoRespository amigoRespository;

    @Autowired
    private AmigoService service;

    @PostMapping
    @Transactional
    public ResponseEntity addAmigo (@RequestBody @Valid DadosInfoAmigo dados) {
        DadosDetalhaAmigo amigo = service.addAmigo(dados);
        return ResponseEntity.ok(amigo);
    }

    @GetMapping("{id}")
    public ResponseEntity<List<DadosDetalhaAmigo>> listarAmigosPorUserId(@PathVariable Long id) {
        List<DadosDetalhaAmigo> listaAmigos = amigoRespository.findAllByUserId(id).stream().map(DadosDetalhaAmigo::new).toList();
        return ResponseEntity.ok(listaAmigos);
    }

    @PostMapping("del")
    @Transactional
    public ResponseEntity desfazerAmizado (@RequestBody @Valid DadosInfoAmigo dados) {
        DadosDetalhaAmigo amigo = service.deletarAmigo(dados);
        return ResponseEntity.ok(amigo);
    }

    // Solicitação

    @PostMapping("preoffer")
    @Transactional
    public ResponseEntity enviarPedido (@RequestBody @Valid DadosRegistroSolicitacao dados) {
        DadosDetalhaSolicitacao solicitacao = service.enviarSolicitacaoAmizade(dados);
        return ResponseEntity.ok(solicitacao);
    }


    @GetMapping("preoffer/all/{id}")
    public ResponseEntity<List<DadosDetalhaSolicitacao>> listarSolicitacoesPorUserId(@PathVariable Long id) {
        List<DadosDetalhaSolicitacao> listaSolicitacao = solicitacaoRepository.findAllByUserDestinatarioId(id).stream().map(DadosDetalhaSolicitacao::new).toList();
        return ResponseEntity.ok(listaSolicitacao);
    }

//    @GetMapping("preoffer/all/{id}")
//    public ResponseEntity<List<DadosDetalhaSolicitacao>> detalhaSolicitacao(@PathVariable Long id) {
//        List<DadosDetalhaSolicitacao> listaSolicitacao = solicitacaoRepository.findAllByUserDestinatarioId(id).stream().map(DadosDetalhaSolicitacao::new).toList();
//        return ResponseEntity.ok(listaSolicitacao);
//    }

    @DeleteMapping("preoffer/{id}")
    @Transactional
    public ResponseEntity cancelarSolicitacao (@PathVariable Long id) {
        service.rejeitarSolicitacaoAmizade(id);
        return ResponseEntity.ok().build();
    }

}
