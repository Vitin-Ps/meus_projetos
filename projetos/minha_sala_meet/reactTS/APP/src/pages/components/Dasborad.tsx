import React, { useContext } from 'react';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { useNavigate } from 'react-router-dom';

interface DashboardProps {
  nome: string;
}

const Dasborad: React.FC<DashboardProps> = ({ nome }) => {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const sairLogin = () => {
    auth.sigout();
    navigate('/');
  };
  return (
    <header className="header_estilo_new">
      <p>Olá! Bem vindo {nome}</p>
      <p className="sair_login" onClick={sairLogin}>
        Sair
      </p>
    </header>
  );
};

export default Dasborad;
