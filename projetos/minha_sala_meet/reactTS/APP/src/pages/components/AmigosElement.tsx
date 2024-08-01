import { faEdit, faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import AddAmigo from './AddAmigo';
import '../../css/AmigosElement.css';
import { Usuario } from '../../interfaces/Usuario';
import { cadastrarAmigo, desfazerAmizade, listarAmigos, listarSolicitacoesPorUserId, rejeitarSolicitacao } from '../../services/AmigosService';
import { Solicitacao } from '../../interfaces/Solicitacao';
import { Amigo } from '../../interfaces/Amigo';

interface AmigosElementProps {
  showInfoUser: React.SetStateAction<boolean>;
  user: Usuario;
}

const AmigosElement: React.FC<AmigosElementProps> = ({ showInfoUser, user }) => {
  const [showAddAmigo, setShowAddAmigo] = useState(false);
  const [amigos, setAmigos] = useState<Amigo[]>([]);
  const [notificacoes, setNotificacoes] = useState<Solicitacao[]>([]);
  //   const [amigosAll, setAmigosAll] = useState<Usuario[]>([]);

  useEffect(() => {
    const buscaDados = async () => {
      const resNotificacao = await listarSolicitacoesPorUserId(user.id!);

      if (resNotificacao.error) {
        alert(resNotificacao.message);
        return;
      }

      setNotificacoes(resNotificacao);

      const resAmigos = await listarAmigos(user.id!);

      if (resAmigos.error) {
        alert(resAmigos.message);
        return;
      }

      setAmigos(resAmigos);
    };
    buscaDados();
  }, []);

  const aceitarSolicitacao = async (notificacaoAdd: Solicitacao) => {
    const res = await cadastrarAmigo(user.id!, notificacaoAdd.userRemetente.id!);

    if (res.error) {
      alert(res.message);
      return;
    }


    setNotificacoes(notificacoes.filter((solicitacao) => solicitacao.id !== notificacaoAdd.id));
    const resAmigos = await listarAmigos(user.id!);

    if (resAmigos.error) {
      alert(resAmigos.message);
      return;
    }

    setAmigos(resAmigos);
  };

  const apagarSolicitacao = async (notificacaoDel: Solicitacao) => {
    const res = await rejeitarSolicitacao(notificacaoDel.id);

    if (res.error) {
      alert(res.message);
      return;
    }

    setNotificacoes(notificacoes.filter((solicitacao) => solicitacao.id !== notificacaoDel.id));
  };

  const desfazerAmigo = async (amigoDel: Amigo) => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente excluir ${amigoDel.amigo.nome} da sua lista?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await desfazerAmizade(user.id!, amigoDel.amigo.id!);
      if (res.error) {
        alert(res.message);
        return;
      }

      setAmigos(amigos.filter((amigo) => amigo.id !== amigoDel.id));
    }
  };

  return (
    <div className={`info_user ${showInfoUser ? 'expand_total' : 'collapse_total'}`}>
      <aside>
        <div className="img_container">
          <img src="./images/avatar.jpg" alt="img_user" />
          <FontAwesomeIcon icon={faEdit} className="icon_edit" />
        </div>
        <h2>Victor Soares</h2>
      </aside>
      <section>
        <AddAmigo showAddAmigo={showAddAmigo} setShowAddAmigo={setShowAddAmigo} user={user} />
        <div className="lista_amigos_container">
          <div className="btn_membros_container">
            <button className="btn_circle btn_add_membro" onClick={() => setShowAddAmigo(true)}>
              <FontAwesomeIcon icon={faPlus} />
            </button>
          </div>
          <h2>Amigos</h2>
          <div className="lista_membros scroll-bar">
            {amigos &&
              amigos.map((amigo) => (
                <div className="card_conversa card_membro" key={amigo.id}>
                  <img src="./images/avatar.jpg" alt="avatar" />
                  <h2>{amigo.amigo.nome}</h2>
                  <button className="btn_circle btn_remover_membro" onClick={() => desfazerAmigo(amigo)}>
                    <FontAwesomeIcon icon={faMinus} />
                  </button>
                </div>
              ))}
          </div>
        </div>
        <div className="lista_notificacao">
          <h2>Notificações</h2>
          <div className="lista_membros scroll-bar">
            {notificacoes &&
              notificacoes.map((notificacao) => (
                <div className="card_membro card_notificacao">
                  <img src="./images/avatar.jpg" alt="avatar" />
                  <h2>{notificacao.userRemetente.nome}</h2>
                  <div className="btn_notificacao_container">
                    <button className="btn_circle btn_add_membro" onClick={() => aceitarSolicitacao(notificacao)}>
                      <FontAwesomeIcon icon={faPlus} />
                    </button>
                    <button className="btn_circle btn_remover_membro" onClick={() => apagarSolicitacao(notificacao)}>
                      <FontAwesomeIcon icon={faMinus} />
                    </button>
                  </div>
                </div>
              ))}
          </div>
        </div>
      </section>
    </div>
  );
};

export default AmigosElement;
