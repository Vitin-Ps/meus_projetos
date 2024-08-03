import { faEdit, faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import AddAmigo from './AddAmigo';
import '../../css/AmigosElement.css';
import { Usuario } from '../../interfaces/Usuario';
import { cadastrarAmigo, desfazerAmizade, listarAmigos, listarSolicitacoesPorUserId, rejeitarSolicitacao } from '../../services/AmigosService';
import { Solicitacao } from '../../interfaces/Solicitacao';
import { Amigo } from '../../interfaces/Amigo';
import { amigoEvent } from '../../services/wss';
import { socket } from '../../services/socket';

interface AmigosElementProps {
  showInfoUser: boolean;
  user: Usuario;
  setCountNotificacao: React.Dispatch<React.SetStateAction<number>>;
  countNotificacao: number;
  notificacoes: Solicitacao[];
  setNotificacoes: React.Dispatch<React.SetStateAction<Solicitacao[]>>;
}

const AmigosElement: React.FC<AmigosElementProps> = ({
  showInfoUser,
  user,
  setCountNotificacao,
  countNotificacao,
  notificacoes,
  setNotificacoes,
}) => {
  const [showAddAmigo, setShowAddAmigo] = useState(false);
  const [amigos, setAmigos] = useState<Amigo[]>([]);

  useEffect(() => {
    const buscaDados = async () => {
      const resAmigos = await listarAmigos(user.id!);

      if (resAmigos.error) {
        alert(resAmigos.message);
        return;
      }

      setAmigos(resAmigos);

      const resNotificacao = await listarSolicitacoesPorUserId(user.id!);
      setNotificacoes(resNotificacao);
      setCountNotificacao(resNotificacao.length);

      if (resNotificacao.error) {
        alert(resNotificacao.message);
        return;
      }

      setNotificacoes(resNotificacao);

      socket.on('receber-amigo-event', (data) => {
        if (data.type === 'add') {
          setAmigos((prevAmigos) => {
            if (!prevAmigos.includes(data.amigo)) {
              return [...prevAmigos, data.amigo];
            }
            return prevAmigos;
          });
        } else {
          setAmigos((prevAmigos) => {
            return prevAmigos.filter((amigo) => amigo.id !== data.amigo.id!);
          });
        }
      });
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
    amigoEvent('add', res);

    const resAmigos = await listarAmigos(user.id!);

    if (resAmigos.error) {
      alert(resAmigos.message);
      return;
    }

    setAmigos(resAmigos);
    setCountNotificacao(countNotificacao - 1);
  };

  const apagarSolicitacao = async (notificacaoDel: Solicitacao) => {
    const res = await rejeitarSolicitacao(notificacaoDel.id);

    if (res.error) {
      alert(res.message);
      return;
    }

    setNotificacoes(notificacoes.filter((solicitacao) => solicitacao.id !== notificacaoDel.id));
    setCountNotificacao(countNotificacao - 1);
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
      amigoEvent('del', res);
    }
  };

  return (
    <div className={`info_user ${showInfoUser ? 'expand_total' : 'collapse_total'}`}>
      <aside>
        <div className="img_container">
          <img src="./images/avatar.jpg" alt="img_user" />
          <FontAwesomeIcon icon={faEdit} className="icon_edit" />
        </div>
        <h2>{user.nome}</h2>
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
                <div className="card_membro card_notificacao" key={notificacao.id}>
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
