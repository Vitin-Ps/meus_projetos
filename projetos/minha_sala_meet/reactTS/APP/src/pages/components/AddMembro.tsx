import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Usuario } from '../../interfaces/Usuario';
import { Grupo } from '../../interfaces/Grupo';
import { detalhaUsuario, listarUsuarios } from '../../services/UsuarioService';
import { addIntegranteGrupo, listarMembrosPorGrupo } from '../../services/ListaMembrosService';

interface AddMembroProps {
  setShowAddMembro: React.Dispatch<React.SetStateAction<boolean>>;
  showAddMembro: React.SetStateAction<boolean>;
  grupo: Grupo;
  userId: number;
  listamembros: React.SetStateAction<Usuario[]>;
  setListaMembros: React.Dispatch<React.SetStateAction<Usuario[]>>;
}

const AddMembro: React.FC<AddMembroProps> = ({ setShowAddMembro, showAddMembro, grupo, userId, setListaMembros, listamembros }) => {
  const [listaAmigos, setListaAmigos] = useState<Usuario[]>([]);

  useEffect(() => {
    const buscaDados = async () => {
      const res = await listarUsuarios();

      if (res.error) {
        alert(res.error);
        return;
      }

      setListaAmigos(res);
    };
    buscaDados();
  }, [showAddMembro]);

  const addmembro = async (id: number) => {
    const res = await addIntegranteGrupo(grupo.id!, id, userId);

    if (res.error) {
      alert(res.error);
    }

    const resUserMembro = await detalhaUsuario(id);
    setListaMembros((listamembros) => [...listamembros, resUserMembro]);
    setShowAddMembro(false);
  };

  return (
    <div className={`info_grupo_container ${showAddMembro ? 'expand_membros' : 'collapse_membros'}`}>
      {/* // <div className={`info_grupo_container teste`}> */}
      <div className="btn_membros_container">
        <button className="btn_circle btn_fechar_info" onClick={() => setShowAddMembro(false)}>
          <FontAwesomeIcon icon={faMinus} />
        </button>
      </div>
      <h2>Amigos</h2>
      <div className="lista_mebros scroll-bar">
        {listaAmigos &&
          listaAmigos.length > 0 &&
          listaAmigos.map(
            (amigo) =>
              amigo.id !== userId && (
                <div className="card_conversa card_membro" key={amigo.id}>
                  <img src="./images/avatar.jpg" alt="avatar" />
                  <h2>{amigo.nome}</h2>
                  {amigo.id !== userId && (
                    <button className="btn_circle btn_add_membro" onClick={() => addmembro(amigo.id!)}>
                      <FontAwesomeIcon icon={faPlus} />
                    </button>
                  )}
                </div>
              ),
          )}
      </div>
    </div>
  );
};

export default AddMembro;
