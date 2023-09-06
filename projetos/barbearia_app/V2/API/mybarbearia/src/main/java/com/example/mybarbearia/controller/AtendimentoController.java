package com.example.mybarbearia.controller;

import com.example.mybarbearia.model.atendimento.AtendimentoService;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import com.example.mybarbearia.model.atendimento.DadosAtualizaAtendimento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {
    @Autowired
    AtendimentoService atendimento; // classe onde toda lógica sera realizada

    @PostMapping
    @Transactional
    public ResponseEntity agendarAtendimento(@RequestBody @Valid DadosAgendamentoAtendimento dados) {
        var atendimentoDados = atendimento.agendar(dados);
        return ResponseEntity.ok(atendimentoDados);
    }

    @PutMapping
    @Transactional
    public ResponseEntity alterarAtendimento(@RequestBody @Valid DadosAtualizaAtendimento dados) {
        var atendimentoDados= atendimento.atualizarDados(dados);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/teste")
    @Transactional
    public ResponseEntity teste() {
//        List<String> duracoes = new ArrayList<>();
//        duracoes.add("30:00");
//        duracoes.add("12:00");
//        duracoes.add("01:00:00");
//        var date = StringEmMinutos.duracaoTotal(duracoes);
//        var horario = StringEmMinutos.converterDateParaString(date);
//        System.out.println(horario);
        LocalDateTime data = LocalDateTime.parse("2023-09-01T14:30:00");
        atendimento.agendar(new DadosAgendamentoAtendimento(1L, 1L, data));
        return ResponseEntity.ok().build();
    }
}
