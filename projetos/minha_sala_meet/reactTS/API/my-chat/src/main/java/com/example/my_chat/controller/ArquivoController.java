package com.example.my_chat.controller;


import com.example.my_chat.infra.FuncionalidadesService;
import com.example.my_chat.infra.file.ArquivoService;
import com.example.my_chat.infra.file.DadosNomeArquivo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("arquivos")
@CrossOrigin(origins = {"*"})
public class ArquivoController {

    @Autowired
    private ArquivoService service;

    @PostMapping
    public ResponseEntity<String> enviarArquivo(@RequestPart("arquivo")MultipartFile arquivo) {
        String urlArquivo = service.enviarArquivo(arquivo, null);
        return ResponseEntity.ok("Download bem Sucedido. Link: " + urlArquivo);
    }

    @GetMapping("/{nomeArquivo:.+}")
    public ResponseEntity<Resource> downloadArquivo(@PathVariable String nomeArquivo, HttpServletRequest request) throws IOException {
        return service.downloadArquivo(nomeArquivo, request);
    }

    @GetMapping
    public ResponseEntity<List<DadosNomeArquivo>> listarArquivos() throws IOException {
        return ResponseEntity.ok(service.listarArquivos());
    }

    @DeleteMapping
    public ResponseEntity deletarArquivos(@RequestBody @Valid List<DadosNomeArquivo> dadosList) {
        for(DadosNomeArquivo dados : dadosList) {service.deletarArquivo(dados.nomeArquivo());}
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity atualizaArquivo (@RequestPart("arquivo") MultipartFile arquivo, @RequestPart("dado") String arquivoAtual) {
        String arquivoAtualSemUrl = FuncionalidadesService.extrairNomeArquivo(arquivoAtual);
        String caminhoArquivo = service.enviarArquivo(arquivo, arquivoAtualSemUrl);
        return ResponseEntity.ok(caminhoArquivo);
    }
}
