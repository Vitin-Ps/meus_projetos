import React from 'react';

interface CardConversaProps {
  entrarGrupo: (id: number) => void;
  nome: string;
  id: number;
}

const CardConversa: React.FC<CardConversaProps> = ({ entrarGrupo, nome, id }) => {
  return (
    <div className="card_user" onClick={() => entrarGrupo(id)}>
      <img src="./images/avatar.jpg" alt="avatar" />
      <h2>{nome}</h2>
    </div>
  );
};

export default CardConversa;
