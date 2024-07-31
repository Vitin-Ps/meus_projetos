import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Usuario } from '../../interfaces/Usuario';
import { Grupo } from '../../interfaces/Grupo';
import { detalhaUsuario, listarUsuarios } from '../../services/UsuarioService';
import { addIntegranteGrupo, listarMembrosPorGrupo } from '../../services/ListaMembrosService';
import Input from './Input';

interface AddAmigoProps {
  setShowAddAmigo: React.Dispatch<React.SetStateAction<boolean>>;
  showAddAmigo: React.SetStateAction<boolean>;
  // grupo: Grupo;
  // userId: number;
  // listamembros: React.SetStateAction<Usuario[]>;
  // setListaMembros: React.Dispatch<React.SetStateAction<Usuario[]>>;
}

const AddAmigo: React.FC<AddAmigoProps> = ({ setShowAddAmigo, showAddAmigo }) => {
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
  }, []);

  const AddAmigo = async (id: number) => {};

  return (
    <div className={`info_grupo_container lista_add_amigos_container  ${showAddAmigo ? 'expand_total' : 'collapse_total'}`}>
      {/* // <div className={`info_grupo_container teste`}> */}
      <div className="btn_membros_container">
        <button className="btn_circle btn_fechar_info" onClick={() => setShowAddAmigo(false)}>
          <FontAwesomeIcon icon={faMinus} />
        </button>
      </div>
      <h2>Adicionar Amigo</h2>
      <Input placeholder="Digite o nome do usuário" label="" tipo="text" />
      <div className="lista_usuarios lista_usuarios scroll-bar">
        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>

        <div className="card_conversa card_membro">
          <img src="./images/avatar.jpg" alt="avatar" />
          <h2></h2>

          <button className="btn_circle btn_add_membro">
            <FontAwesomeIcon icon={faPlus} />
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddAmigo;
