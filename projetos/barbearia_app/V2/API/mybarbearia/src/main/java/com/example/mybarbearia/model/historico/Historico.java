//package com.example.mybarbearia.model.historico;
//
//import com.example.mybarbearia.model.atendimento.Atendimento;
//import com.example.mybarbearia.model.funcionario.Funcionario;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//
//@Table(name = "historico")
//@Entity(name = "Historico")
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(of = "id")
//public class Historico {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_atendimento")
//    private Atendimento atendimento;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_funcionario")
//    private Funcionario funcionario;
//    private BigDecimal valorTotal;
//    private BigDecimal comissao;
//
//    public void atualizaComissao(BigDecimal comissao) {
//        if(comissao != null) {
//            this.comissao = comissao;
//        }
//    }
//}
