import React from 'react';
import { Mensagem } from '../../interfaces/Mensagem';

interface CardMensagemProps {
  conversa: Mensagem;
  tipoMsg: string;
}

const CardMensagem: React.FC<CardMensagemProps> = ({ conversa, tipoMsg }) => {
  return (
    <div className={`card_msg ${tipoMsg}`}>
      <p>{conversa.mensagem}</p>
      <span>
        {new Date(conversa.data).getHours()}:{new Date(conversa.data).getMinutes()}
      </span>
    </div>
  );
};

export default CardMensagem;
