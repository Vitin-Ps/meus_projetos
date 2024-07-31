import { faEdit, faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useState } from 'react';
import AddAmigo from './AddAmigo';

interface AmigosElementProps {
  showInfoUser: React.SetStateAction<boolean>;
  // grupo: Grupo;
  // userId: number;
  // listamembros: React.SetStateAction<Usuario[]>;
  // setListaMembros: React.Dispatch<React.SetStateAction<Usuario[]>>;
}

const AmigosElement: React.FC<AmigosElementProps> = ({ showInfoUser }) => {
  const [showAddAmigo, setShowAddAmigo] = useState(false);
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
        <AddAmigo showAddAmigo={showAddAmigo} setShowAddAmigo={setShowAddAmigo} />
        <div className="lista_amigos_container">
          <div className="btn_membros_container">
            <button className="btn_circle btn_add_membro" onClick={() => setShowAddAmigo(true)}>
              <FontAwesomeIcon icon={faPlus} />
            </button>
          </div>
          <h2>Amigos</h2>
          <div className="lista_mebros scroll-bar">
            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>

            <div className="card_conversa card_membro">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{}</h2>
              <button className="btn_circle btn_remover_membro">
                <FontAwesomeIcon icon={faMinus} />
              </button>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default AmigosElement;
