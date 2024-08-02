import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Grupo } from '../../interfaces/Grupo';
import { detalharUserAutoridade, listarMembrosPorGrupo, removeMembroGrupo } from '../../services/ListaMembrosService';
import { ListaMembros } from '../../interfaces/ListaMembros';
import { Usuario } from '../../interfaces/Usuario';
import { deletaGrupo } from '../../services/GrupoService';
import AddMembro from './AddMembro';

interface InfoGrupoProps {
  setShowInfoGrupo: React.Dispatch<React.SetStateAction<boolean>>;
  showInfoGrupo: boolean;
  grupo: Grupo;
  userId: number;
}

const InfoGrupo: React.FC<InfoGrupoProps> = ({ grupo, setShowInfoGrupo, showInfoGrupo, userId }, ref) => {
  const [listaMembros, setlistaMembros] = useState<Usuario[]>([]);
  const [userListaSitucao, setUserListaSitucao] = useState<ListaMembros>();
  const [showAddMembro, setShowAddMembro] = useState(false);

  useEffect(() => {
    const buscarDados = async () => {
      if (grupo) {
        const res = await listarMembrosPorGrupo(grupo.id!);

        if (res.error) {
          alert(res.message);
          return;
        }

        setlistaMembros(res);

        const resUser = await detalharUserAutoridade(userId, grupo.id!);

        if (resUser.error) {
          alert(res.message);
          return;
        }

        setUserListaSitucao(resUser);
      }
    };
    buscarDados();
  }, [grupo]);

  const excluirMembro = async (membroSelecionado: Usuario) => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente excluir o membro ${membroSelecionado.nome}?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await removeMembroGrupo(membroSelecionado.id!, userId, grupo.id!);

      if (res.error) {
        alert(res.message);
        return;
      }

      setlistaMembros(listaMembros.filter((membro) => membro.id !== membroSelecionado.id));
      setShowInfoGrupo(false);
    }
  };

  const apagarGrupo = async () => {
    /* eslint-disable no-restricted-globals */
    const deleteConfig = confirm(`Deseja realmente excluir esse grupo?`);
    /* eslint-enable no-restricted-globals */

    if (deleteConfig) {
      const res = await deletaGrupo(grupo.id!, userId);

      if (res.error) {
        alert(res.message);
        return;
      }

      window.location.href = '/contacts';
    }
  };

  return showAddMembro ? (
    <AddMembro
      grupo={grupo}
      userId={userId}
      setShowAddMembro={setShowAddMembro}
      showAddMembro={showAddMembro}
      setListaMembros={setlistaMembros}
      listamembros={listaMembros}
    />
  ) : (
    <div className={`info_grupo_container ${showInfoGrupo ? 'expand_membros' : 'collapse_membros'}`}>
      <div className="btn_membros_container">
        {userListaSitucao?.cargo === 'ADMIN' && (
          <button className="btn_circle btn_add_membro" onClick={() => setShowAddMembro(true)}>
            <FontAwesomeIcon icon={faPlus} />
          </button>
        )}

        <button className="btn_circle btn_fechar_info" onClick={() => setShowInfoGrupo(false)}>
          <FontAwesomeIcon icon={faMinus} />
        </button>
      </div>
      <h2>Membros</h2>
      <div className="lista_mebros scroll-bar">
        {listaMembros &&
          listaMembros.length > 0 &&
          listaMembros.map((membro) => (
            <div className="card_conversa card_membro" key={membro.id}>
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{membro.nome}</h2>
              {membro.id !== userId && userListaSitucao?.cargo === 'ADMIN' && (
                <button className="btn_circle btn_remover_membro" onClick={() => excluirMembro(membro)}>
                  <FontAwesomeIcon icon={faMinus} />
                </button>
              )}
            </div>
          ))}
      </div>
      <button className="btn_apagar_grupo" onClick={apagarGrupo}>
        Apagar grupo
      </button>
    </div>
  );
};

export default InfoGrupo;
