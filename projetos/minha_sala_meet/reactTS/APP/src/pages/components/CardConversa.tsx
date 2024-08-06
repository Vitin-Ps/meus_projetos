import React from 'react';

interface CardConversaProps {
  entrarConversa: (id: number) => void;
  nome: string;
  id: number;
}

const CardConversa: React.FC<CardConversaProps> = ({entrarConversa, nome, id }) => {
  return (
    <div className="card_user" onClick={() => entrarConversa(id)}>
      <img src="./images/avatar.jpg" alt="avatar" />
      <h2>{nome}</h2>
    </div>
  );
};

export default CardConversa;
