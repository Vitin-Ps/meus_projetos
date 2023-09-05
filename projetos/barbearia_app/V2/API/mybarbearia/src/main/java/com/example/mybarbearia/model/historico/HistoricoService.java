//package com.example.mybarbearia.model.historico;
//
//import com.example.mybarbearia.model.atendimento.Atendimento;
//import com.example.mybarbearia.model.recibo.Recibo;
//import com.example.mybarbearia.repository.HistoricoRepository;
//import com.example.mybarbearia.repository.ReciboRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//public class HistoricoService {
//    @Autowired
//    HistoricoRepository historicoRepository;
//    @Autowired
//    ReciboRepository reciboRepository;
//
//    public void guardarInformacoesDoAtendimento(Atendimento atendimento) {
//        var historico = new Historico(null, atendimento, atendimento.getFuncionario(), atendimento.getPrecoTotal(), null);
//        historicoRepository.save(historico);
//        var comissao = this.processarInformacoes(atendimento);
//        historico.atualizaComissao(comissao);
//
//    }
//
//    private BigDecimal processarInformacoes(Atendimento atendimento) {
//        var dadosAtendimento = reciboRepository.getReferenceByAtendimentoId(atendimento.getId());
//        BigDecimal comissao = BigDecimal.ZERO;
//
//        for (Recibo at : dadosAtendimento) {
//            if(at.getProduto() != null) {
//                comissao.add(at.getProduto().getComissao().multiply(BigDecimal.valueOf(at.getQuantidade())));
//            }
//            if(at.getServico() != null) {
//                BigDecimal precoServicos = at.getServico().getPreco().multiply(BigDecimal.valueOf(at.getQuantidade()));
//                comissao.add(precoServicos.multiply(BigDecimal.valueOf(atendimento.getFuncionario().getPorcentagemComissao() / 100)));
//            }
//        }
//        return comissao;
//    }
//}
