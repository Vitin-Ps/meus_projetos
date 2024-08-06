import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Usuario } from '../../interfaces/Usuario';
import {  listarUsuarios } from '../../services/UsuarioService';
import Input from './Input';
import { removerAcentuacoes } from '../../services/FuncionalidadesService';
import { enviarSoliticatacao } from '../../services/AmigosService';
import { enviarNotificacao } from '../../services/wss';

interface AddAmigoProps {
  setShowAddAmigo: React.Dispatch<React.SetStateAction<boolean>>;
  showAddAmigo: boolean;
  user: Usuario;
}

const AddAmigo: React.FC<AddAmigoProps> = ({ setShowAddAmigo, showAddAmigo, user }) => {
  const [usuarios, setUsuarios] = useState<Usuario[]>([]);
  const [usuariosAll, setUsuariosAll] = useState<Usuario[]>([]);

  useEffect(() => {
    const buscaDados = async () => {
      const listaUsuarios = await listarUsuarios();

      if (listaUsuarios.error) {
        alert(listaUsuarios.message);
        return;
      }

      setUsuarios(listaUsuarios);
      setUsuariosAll(listaUsuarios);
    };
    buscaDados();
  }, []);

  const pesquisaUsuarios = (pesquisa: string) => {
    const valor = removerAcentuacoes(pesquisa);
    setUsuarios(
      usuariosAll.filter((usuario) => {
        const nome = removerAcentuacoes(usuario.nome);
        return nome.includes(valor);
      }),
    );
  };

  const envarSolicitacaoAmigo = async (id: number) => {
    const res = await enviarSoliticatacao(user.id!, id);

    if (res.error) {
      alert(res.message);
      return;
    }

    alert('Solicitação enviada!');
    setShowAddAmigo(false);
    enviarNotificacao(res);
  };

  return (
    <div className={`info_grupo_container lista_add_amigos_container  ${showAddAmigo ? 'expand_total' : 'collapse_total'}`}>
      {/* // <div className={`info_grupo_container teste`}> */}
      <div className="btn_membros_container">
        <button className="btn_circle btn_fechar_info" onClick={() => setShowAddAmigo(false)}>
          <FontAwesomeIcon icon={faMinus} />
        </button>
      </div>
      <h2>Adicionar Amigo</h2>
      <Input placeholder="Digite o nome do usuário" label="" tipo="text" onInput={(valor) => pesquisaUsuarios(valor)} />
      <div className="lista_users scroll-bar">
        {usuarios &&
          usuarios.map(
            (usuario) =>
              usuario.id !== user.id && (
                <div className="card_user" key={usuario.id!}>
                  <img src="./images/avatar.jpg" alt="avatar" />
                  <h2>{usuario.nome}</h2>

                  <div className='btn_card_user_container'>
                    <button className="btn_circle btn_add_membro" onClick={() => envarSolicitacaoAmigo(usuario.id!)}>
                      <FontAwesomeIcon icon={faPlus} />
                    </button>
                  </div>
                </div>
              ),
          )}
      </div>
    </div>
  );
};

export default AddAmigo;
