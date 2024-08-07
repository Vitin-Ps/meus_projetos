import React from 'react';
import { ConversaTipos } from '../../interfaces/Conversa';

interface CardConversaProps {
  entrarConversa: (conversa: ConversaTipos) => void;
  nome: string;
  conversa: ConversaTipos;
}

const CardConversa: React.FC<CardConversaProps> = ({entrarConversa, nome, conversa }) => {
  return (
    <div className="card_user" onClick={() => entrarConversa(conversa)}>
      <img src="./images/avatar.jpg" alt="avatar" />
      <h2>{nome}</h2>
    </div>
  );
};

export default CardConversa;
