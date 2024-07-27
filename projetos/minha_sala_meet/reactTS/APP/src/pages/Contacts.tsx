import { useEffect, useState } from 'react';
import '../css/Contacts.css';
import { addMensagem, registraSocketEventos } from '../services/wss';
import socket from '../services/socket';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';

const Contacts = () => {
  return (
    <>
      <section className="contatos_container">
        <div className="conversas_container">
          <aside>
            <h2>Conversas</h2>
          </aside>
          <div className="conversas_main_container">
            <input type="text" placeholder="Pesquisar grupos" className="input_pesquisar_grupos" />
            <div className="grupos_container">
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
              <div className="card_conversa">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
            </div>
          </div>
        </div>
        <div className="mensagens_container">
          <aside>
            <div className="card_avatar">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>Fulano</h2>
            </div>
          </aside>
          <div className="mensagens_main_container">
            <div className="conteudo_container"></div>
            <div className="nova_mensagem_container">
              <input type="text" id="nova_mensagem" />
              <button>{<FontAwesomeIcon icon={faPaperPlane} />}</button>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default Contacts;
