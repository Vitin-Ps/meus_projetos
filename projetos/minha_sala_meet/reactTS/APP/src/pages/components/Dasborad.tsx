import React, { useContext } from 'react';
import '../../css/dashboard.css';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell } from '@fortawesome/free-solid-svg-icons';

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
      <div className="dashboard_func">
        <div className="notificacao_status_container">
          <span className='notificacao_count'>0</span>
          <FontAwesomeIcon icon={faBell} className='icon_notificacao'/>
        </div>
        <p className="text_decoration_blue" onClick={sairLogin}>
          Sair
        </p>
      </div>
    </header>
  );
};

export default Dasborad;
