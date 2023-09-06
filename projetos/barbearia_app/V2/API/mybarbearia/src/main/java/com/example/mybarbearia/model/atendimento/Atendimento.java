package com.example.mybarbearia.model.atendimento;

import com.example.mybarbearia.services.StringEmMinutos;
import com.example.mybarbearia.model.cliente.Cliente;
import com.example.mybarbearia.model.funcionario.Funcionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "atendimento")
@Entity(name = "Atendimento")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;
    private LocalDateTime data;
    private String duracao;
    private BigDecimal precoTotal;
    @Enumerated(EnumType.STRING)
    private StatusAtendimento status;

    public void addPrecoEDuracao(String duracao, BigDecimal preco) {
        List<String> duracoes = new ArrayList<>();
        duracoes.add(duracao);
        duracoes.add(this.duracao);
        // adiciona a duração anterior total com as novas

        if(this.precoTotal != null) this.precoTotal = this.precoTotal.add(preco); // adiciona o preco ao preco total se ja conter um valor pro precoTotal
        else this.precoTotal = preco;
        this.duracao = StringEmMinutos.converterDateParaString(StringEmMinutos.duracaoTotal(duracoes)); // usa o petodo para somar as duracoes e salva um string
    }

    public void atualizaDados(Funcionario funcionario, LocalDateTime data, StatusAtendimento status) {
        if(funcionario != null) {
            this.funcionario = funcionario;
        }
        if(data != null) {
            this.data = data;
        }
        if(status != null) {
            this.status = status;
        }
    }
}
