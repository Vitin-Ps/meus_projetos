
package com.example.my_chat.domain.conversa;

import com.example.my_chat.domain.grupo.DadosDetalhaGrupo;
import com.example.my_chat.domain.grupo.GrupoService;
import com.example.my_chat.domain.privado.DadosInfoPrivado;
import com.example.my_chat.domain.privado.DadosRegistroPrivado;
import com.example.my_chat.domain.privado.Privado;
import com.example.my_chat.domain.usuario.Usuario;
import com.example.my_chat.infra.exception.ValidacaoException;
import com.example.my_chat.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversaService {

    @Autowired
    private MembroRepository membroRepository;

    @Autowired
    private PrivadoRepository privadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConversaRepository conversaRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    public List<DadosDetalhaConversaTipos> RetornaListaConversaPorUser(Long id) {
        List<DadosDetalhaConversaTipos> listaDeConversas = new ArrayList<>();

        membroRepository.findAllByUsuarioId(id).stream().map(DadosDetalhaGrupo::new).toList().forEach(grupo -> {
            listaDeConversas.add(new DadosDetalhaConversaTipos(grupo));
        });

        privadoRepository.findAllByUserOneId(id).forEach(privado -> {
            listaDeConversas.add(new DadosDetalhaConversaTipos(privado));
        });


        return listaDeConversas;
    }

    public List<DadosDetalhaConversaTipos> cadastraPrivado(DadosRegistroPrivado dados) {

        List<DadosDetalhaConversaTipos> listaPrivados = new ArrayList<>();

        Privado privadoExitente = privadoRepository.getReferenceByUserOneIdAndUserTwoId(dados.user_one_id(), dados.user_two_id());

        if(privadoExitente != null) {
            throw new ValidacaoException("Você já tem uma conversa com esse usuário");
        }
        privadoExitente = privadoRepository.getReferenceByUserOneIdAndUserTwoId(dados.user_two_id(), dados.user_one_id());

        if(privadoExitente != null) {
            throw new ValidacaoException("Você já tem uma conversa com esse usuário");
        }

        Conversa conversa = new Conversa(dados.uuid(), TipoConversa.PRIVADO);

        conversa = conversaRepository.save(conversa);

        Usuario userOne = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_one_id());
        Usuario userTwo = usuarioRepository.getReferenceByIdAndAtivoTrue(dados.user_two_id());

        Privado privado = new Privado(userOne, userTwo, conversa);

        privado = privadoRepository.save(privado);

        listaPrivados.add(new DadosDetalhaConversaTipos(privado));

        privado = new Privado(userTwo, userOne, conversa);

        privado = privadoRepository.save(privado);

        listaPrivados.add(new DadosDetalhaConversaTipos(privado));

        return listaPrivados;
    }

    public void deletarTodasMensagens() {
        mensagemRepository.deleteAll();
        membroRepository.deleteAll();
        grupoRepository.deleteAll();
        privadoRepository.deleteAll();
        conversaRepository.deleteAll();
    }

    public DadosDetalhaConversaTipos detalharConversaPorId(DadosInfoConversaUser dados) {
        Conversa conversa = conversaRepository.getReferenceById(dados.conversa_id());
        try {
            DadosDetalhaConversaTipos conversaTipos = null;

            if(conversa.getTipo().equals(TipoConversa.GRUPO)) {
                conversaTipos = new DadosDetalhaConversaTipos(new DadosDetalhaGrupo(grupoRepository.getReferenceByConversaId(conversa.getId())));
            } else {
                conversaTipos = new DadosDetalhaConversaTipos(privadoRepository.getReferenceByConversaIdAndUserOneId(conversa.getId(), dados.user_one_id()));
            }

            return conversaTipos;
        } catch (EntityNotFoundException exception) {
            throw new ValidacaoException("Conversa não encontrada!");
        }
    }

    public  List<DadosDetalhaConversaTipos>  deletarPrivado(DadosInfoPrivado dados) {
        List<DadosDetalhaConversaTipos> privados = new ArrayList<>();

        Privado privadoOne = privadoRepository.getReferenceByUserOneIdAndUserTwoId(dados.user_one_id(), dados.user_two_id());
        Privado privadoTwo = privadoRepository.getReferenceByUserOneIdAndUserTwoId(dados.user_two_id(), dados.user_one_id());

        if(privadoTwo == null || privadoOne == null) {
            throw new ValidacaoException("Está conversa não existe!");
        }
        mensagemRepository.deleteAllByConversaId(privadoOne.getConversa().getId());

        privadoRepository.delete(privadoOne);
        privadoRepository.delete(privadoTwo);

        conversaRepository.delete(privadoOne.getConversa());

        privados.add(new DadosDetalhaConversaTipos(privadoOne));
        privados.add(new DadosDetalhaConversaTipos(privadoTwo));

        return privados;
    }
}
