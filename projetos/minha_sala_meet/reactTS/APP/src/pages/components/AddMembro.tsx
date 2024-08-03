import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Usuario } from '../../interfaces/Usuario';
import { Grupo } from '../../interfaces/Grupo';
import { detalhaUsuario } from '../../services/UsuarioService';
import { addIntegranteGrupo } from '../../services/MembroService';
import { listarAmigos } from '../../services/AmigosService';
import { Amigo } from '../../interfaces/Amigo';
import { grupoEvent } from '../../services/wss';

interface AddMembroProps {
  setShowAddMembro: React.Dispatch<React.SetStateAction<boolean>>;
  showAddMembro: boolean;
  grupo: Grupo;
  userId: number;
  listamembros: Usuario[];
  setListaMembros: React.Dispatch<React.SetStateAction<Usuario[]>>;
}

const AddMembro: React.FC<AddMembroProps> = ({ setShowAddMembro, showAddMembro, grupo, userId, setListaMembros }) => {
  const [listaAmigos, setListaAmigos] = useState<Amigo[]>([]);

  useEffect(() => {
    const buscaDados = async () => {
      const res = await listarAmigos(userId);

      if (res.error) {
        alert(res.message);
        return;
      }

      setListaAmigos(res);
    };
    buscaDados();
  }, [showAddMembro]);

  const addmembro = async (id: number) => {
    const res = await addIntegranteGrupo(grupo.id!, id, userId);

    if (res.error) {
      alert(res.message);
      return;
    }

    const resUserMembro = await detalhaUsuario(id);
    setListaMembros((listamembros) => [...listamembros, resUserMembro]);
    setShowAddMembro(false);

    grupoEvent(String(id), grupo, 'add');
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
                  <h2>{amigo.amigo.nome}</h2>
                  {amigo.id !== userId && (
                    <button className="btn_circle btn_add_membro" onClick={() => addmembro(amigo.amigo.id!)}>
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
