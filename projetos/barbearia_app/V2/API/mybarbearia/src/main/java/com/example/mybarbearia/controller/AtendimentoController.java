package com.example.mybarbearia.controller;

import com.example.mybarbearia.infra.services.StringEmMinutos;
import com.example.mybarbearia.model.atendimento.AtendimentoService;
import com.example.mybarbearia.model.atendimento.DadosAgendamentoAtendimento;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {
    @Autowired
    AtendimentoService atendimento;

    @PostMapping
    @Transactional
    public ResponseEntity agendarAtendimento(@RequestBody @Valid DadosAgendamentoAtendimento dados) {
        var atendimentoDados = atendimento.agendar(dados);
        return ResponseEntity.ok(atendimentoDados);
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
