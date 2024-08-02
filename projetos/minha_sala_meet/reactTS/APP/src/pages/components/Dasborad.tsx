import React, { useContext, useEffect, useState } from 'react';
import '../../css/dashboard.css';
import { AuthContext } from '../../contexts/Auth/AuthContext';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell } from '@fortawesome/free-solid-svg-icons';
import { Solicitacao } from '../../interfaces/Solicitacao';

interface DashboardProps {
  nome: string;
  setShowInfoUser: React.Dispatch<React.SetStateAction<boolean>>;
  showInfoUser: boolean;
  countNotificacao: number;
}

const Dasborad: React.FC<DashboardProps> = ({ nome, setShowInfoUser, showInfoUser, countNotificacao }) => {
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
        <div className={`notificacao_status_container ${countNotificacao > 0 && 'com_notificacao'}`}>
          {countNotificacao > 0 && <span className="notificacao_count">{countNotificacao}</span>}

          <FontAwesomeIcon icon={faBell} className="icon_notificacao" />
        </div>
        <p className="text_decoration_blue" onClick={sairLogin}>
          Sair
        </p>
      </div>
    </header>
  );
};

export default Dasborad;
