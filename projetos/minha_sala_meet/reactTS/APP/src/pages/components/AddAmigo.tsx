import { faMinus, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect, useState } from 'react';
import { Usuario } from '../../interfaces/Usuario';
import { Grupo } from '../../interfaces/Grupo';
import { detalhaUsuario, listarUsuarios } from '../../services/UsuarioService';
import { addIntegranteGrupo, listarMembrosPorGrupo } from '../../services/ListaMembrosService';
import Input from './Input';
import { removerAcentuacoes } from '../../services/FuncionalidadesService';
import { enviarSoliticatacao } from '../../services/AmigosService';

interface AddAmigoProps {
  setShowAddAmigo: React.Dispatch<React.SetStateAction<boolean>>;
  showAddAmigo: React.SetStateAction<boolean>;
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
      <div className="lista_usuarios lista_usuarios scroll-bar">
        {usuarios &&
          usuarios.map(
            (usuario) =>
              usuario.id !== user.id && (
                <div className="card_membro card_notificacao">
                  <img src="./images/avatar.jpg" alt="avatar" />
                  <h2>{usuario.nome}</h2>

                  <button className="btn_circle btn_add_membro" onClick={() => envarSolicitacaoAmigo(usuario.id!)}>
                    <FontAwesomeIcon icon={faPlus} />
                  </button>
                </div>
              ),
          )}
      </div>
    </div>
  );
};

export default AddAmigo;
