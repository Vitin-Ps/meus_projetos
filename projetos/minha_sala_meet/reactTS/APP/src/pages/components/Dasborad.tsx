import React, { useContext } from 'react';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { useNavigate } from 'react-router-dom';

interface DashboardProps {
  nome: string;
  setShowInfoUser: React.Dispatch<React.SetStateAction<boolean>>;
  showInfoUser: React.SetStateAction<boolean>;
}

const Dasborad: React.FC<DashboardProps> = ({ nome, setShowInfoUser, showInfoUser }) => {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const sairLogin = () => {
    auth.sigout();
    navigate('/');
  };
  return (
    <header className="header_estilo_new">
      <p>
        Olá! Bem vindo{' '}
        <span className="text_decoration_blue" onClick={() => setShowInfoUser(!showInfoUser)}>
          {nome}
        </span>
      </p>
      <p className="text_decoration_blue" onClick={sairLogin}>
        Sair
      </p>
    </header>
  );
};

export default Dasborad;
