import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Grupo } from '../../interfaces/Grupo';
import { detalharUserAutoridade, listarMembrosPorGrupo, removeMembroGrupo, sairGrupo } from '../../services/MembroService';
import { ListaMembros } from '../../interfaces/ListaMembros';
import { Usuario } from '../../interfaces/Usuario';
import { deletaGrupo } from '../../services/GrupoService';
import AddMembro from './AddMembro';
import { grupoEvent } from '../../services/wss';
import { ConversaTipos } from '../../interfaces/Conversa';

interface InfoGrupoProps {
  setShowInfoConversa: React.Dispatch<React.SetStateAction<boolean>>;
  showInfoConversa: boolean;
  membros: Usuario[];
  setMembros: React.Dispatch<React.SetStateAction<Usuario[]>>;
  conversa: ConversaTipos;
  user: Usuario;
}

const InfoConversa: React.FC<InfoGrupoProps> = ({ conversa, setShowInfoConversa, showInfoConversa, user, setMembros, membros }) => {
  const [userListaSituacao, setUserListaSituacao] = useState<ListaMembros>();
  const [showAddMembro, setShowAddMembro] = useState(false);

  useEffect(() => {
    const buscarDados = async () => {
      if (conversa.grupo) {
        const res = await listarMembrosPorGrupo(conversa.grupo.id!);

        if (res.error) {
          alert(res.message);
          return;
        }

        setMembros(res);

        const resUser = await detalharUserAutoridade(user.id!, conversa.grupo.id!);

        if (resUser.error) {
          alert(res.message);
          return;
        }

        setUserListaSituacao(resUser);
      }
    };
    buscarDados();
  }, [conversa]);

  const excluirMembro = async (membroSelecionado: Usuario) => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente excluir o membro ${membroSelecionado.nome}?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await removeMembroGrupo(membroSelecionado.id!, user.id!, conversa.grupo!.id!);

      if (res.error) {
        alert(res.message);
        return;
      }

      setMembros(membros.filter((membro) => membro.id !== membroSelecionado.id));
      setShowInfoConversa(false);

      grupoEvent(String(membroSelecionado.id!), conversa.grupo!, 'del');
    }
  };

  const apagarGrupo = async () => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente excluir esse grupo?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await deletaGrupo(conversa.grupo!.id!, user.id!);

      if (res.error) {
        alert(res.message);
        return;
      }

      setShowInfoConversa(false);
      grupoEvent(conversa.grupo!.conversa.uuid, conversa.grupo!, 'del-group');
    }
  };

  const sairMembroGrupo = async () => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente sair deste grupo?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await sairGrupo(user.id!);

      if (res.error) {
        alert(res.message);
        return;
      }

      setShowInfoConversa(false);
      grupoEvent(conversa.grupo!.conversa.uuid, conversa.grupo!, 'sair-group', user);
    }
  };

  return conversa.grupo ? (
    showAddMembro ? (
      <AddMembro
        grupo={conversa.grupo!}
        userId={user.id!}
        setShowAddMembro={setShowAddMembro}
        showAddMembro={showAddMembro}
        setListaMembros={setMembros}
        listamembros={membros}
      />
    ) : (
      <div className={`info_grupo_container ${showInfoConversa ? 'expand_membros' : 'collapse_membros'}`}>
        <div className="btn_membros_container">
          {userListaSituacao?.cargo === 'ADMIN' && (
            <button className="btn_circle btn_add_membro" onClick={() => setShowAddMembro(true)}>
              <FontAwesomeIcon icon={faPlus} />
            </button>
          )}

          <button className="btn_circle btn_fechar_info" onClick={() => setShowInfoConversa(false)}>
            <FontAwesomeIcon icon={faMinus} />
          </button>
        </div>
        <h2>Membros</h2>
        <div className="lista_users scroll-bar">
          {membros &&
            membros.length > 0 &&
            membros.map((membro) => (
              <div className="card_user card_membro" key={membro.id}>
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>{membro.nome}</h2>
                {membro.id !== user.id! && userListaSituacao?.cargo === 'ADMIN' && (
                  <div className="btn_card_user_container">
                    <button className="btn_circle btn_remover_membro" onClick={() => excluirMembro(membro)}>
                      <FontAwesomeIcon icon={faMinus} />
                    </button>
                  </div>
                )}
              </div>
            ))}
        </div>
        {userListaSituacao?.cargo === 'ADMIN' ? (
          <button className="btn_apagar_grupo" onClick={apagarGrupo}>
            Apagar grupo
          </button>
        ) : (
          <button className="btn_apagar_grupo" onClick={sairMembroGrupo}>
            Sair do grupo
          </button>
        )}
      </div>
    )
  ) : (
    <div></div>
  );
};

export default InfoConversa;
