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

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {
    @Autowired
    AtendimentoService atendimento;

    @PostMapping
    @Transactional
    public ResponseEntity agendarAtendimento(@RequestBody @Valid DadosAgendamentoAtendimento dados) {
        atendimento.agendar(dados);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/teste")
    @Transactional
    public ResponseEntity teste() {
        LocalDateTime data = LocalDateTime.parse("2023-09-01T14:30:00");
        atendimento.agendar(new DadosAgendamentoAtendimento(1L, null, data));
        return ResponseEntity.ok().build();
    }
}
