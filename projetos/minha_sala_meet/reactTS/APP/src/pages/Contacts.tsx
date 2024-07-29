import '../css/Contacts.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { useContext, useEffect, useState } from 'react';
import { Mensagem } from '../interfaces/Mensagem';
import { addCodigoUser, addMensagem, entrarSala } from '../services/wss';
import socket from '../services/socket';
import CardMensagem from './components/CardMensagem';
import Dasborad from './components/Dasborad';
import { AuthContext } from '../contexts/Auth/AuthContext';
import { Grupo } from '../interfaces/Grupo';
import { detalharGrupo, listarGruposPorUser } from '../services/GrupoService';
import CardConversa from './components/CardConversa';

const conversas2: Mensagem[] = [
  {
    id: 1,
    grupo: {
      id: 5,
      uuid: 'dfghjjjj',
      nome: 'Os brabo da progamação',
    },
    usuario_remetente: {
      id: 2,
      nome: 'Victin',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
  {
    id: 2,
    grupo: {
      id: 5,
      uuid: 'dfghjjjj',
      nome: 'Os brabo da progamação',
    },
    usuario_remetente: {
      id: 3,
      nome: 'Marcos',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
  {
    id: 3,
    grupo: {
      id: 5,
      uuid: 'dfghjjjj',
      nome: 'Os brabo da progamação',
    },
    usuario_remetente: {
      id: 3,
      nome: 'mateus',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
  {
    id: 4,
    grupo: {
      id: 5,
      uuid: 'dfghjjjj',
      nome: 'Os brabo da progamação',
    },
    usuario_remetente: {
      id: 2,
      nome: 'Victin',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
  {
    id: 5,
    grupo: {
      id: 5,
      uuid: 'dfghjjjj',
      nome: 'Os brabo da progamação',
    },
    usuario_remetente: {
      id: 3,
      nome: 'MAteus',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
];

const socketIO = socket;

const Contacts = () => {
  const [grupoSelecionado, setGrupoSelecionado] = useState<Grupo>();
  const [seusGrupos, setSeusGrupos] = useState<Grupo[]>();
  const [mensagem, setMensagem] = useState('');
  const [conversas, setConversas] = useState<Mensagem[]>(conversas2);
  const [showConversa, setShowConversa] = useState(false);

  const auth = useContext(AuthContext);

  useEffect(() => {
    const handleMensagem = (data: Mensagem) => {
      console.log('chegou aqui na mensagem', data);
      setConversas((prevConversas) => [...prevConversas, data]);
    };

    // Registrar o listener para o evento
    socketIO.on('receberMensagem', handleMensagem);

    // Limpeza para remover o listener quando o componente for desmontado

    const carregaDados = async () => {
      const grupos = await listarGruposPorUser(auth.user!.id!);
      setSeusGrupos(grupos);
    };
    carregaDados();
    return () => {
      socketIO.off('receberMensagem', handleMensagem);
    };
  }, []);

  const entrarGrupo = async (id: number) => {
    const grupo: Grupo = await detalharGrupo(id);
    if (grupo) {
      setGrupoSelecionado(grupo);
      entrarSala(socketIO, grupo.uuid);

      // implementar lógica de mensagens antigas
      setShowConversa(true);
    } else {
      alert('Grupo não encontrado');
    }
  };

  const enviarMensagem = () => {
    console.log(mensagem);

    const novaMensagem: Mensagem = {
      id: conversas.length + 1,
      data_hora: new Date(),
      grupo: grupoSelecionado!,
      usuario_remetente: auth.user!,
      mensagem,
    };

    addMensagem(socketIO, novaMensagem);
    setConversas([...conversas, novaMensagem]);
    setMensagem('');
  };

  return (
    <>
      <Dasborad nome={auth.user?.nome!} />
      <section className="contatos_container">
        <div className="conversas_container">
          <aside>
            <h2>Conversas</h2>
          </aside>
          <div className="conversas_main_container">
            <input type="text" placeholder="Pesquisar grupos" className="input_pesquisar_grupos" />
            <div className="grupos_container">
              {seusGrupos && seusGrupos.map((grupo) => <CardConversa key={grupo.id} entrarGrupo={entrarGrupo} nome={grupo.nome} id={grupo.id!} />)}
            </div>
          </div>
        </div>
        <div className="mensagens_container">
          <aside>
            {grupoSelecionado && (
              <div className="card_avatar">
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>{grupoSelecionado.nome}</h2>
              </div>
            )}
          </aside>
          <div className="mensagens_main_container">
            <div className="conteudo_container">
              {showConversa &&
                conversas.length > 0 &&
                conversas.map((conversa) => (
                  <div key={conversa.id} className="card_msg_pai">
                    {conversa.usuario_remetente.id === auth.user!.id! ? (
                      <CardMensagem key={`user-${conversa.id}`} conversa={conversa} tipoMsg="msg_user" />
                    ) : (
                      <div className="card_msg_integrante" key={`integrante-${conversa.id}`}>
                        <span>{conversa.usuario_remetente.nome}</span>
                        <CardMensagem key={`msg-${conversa.id}`} conversa={conversa} tipoMsg="msg_integrante" />
                      </div>
                    )}
                  </div>
                ))}
            </div>
            <div className="nova_mensagem_container">
              <input type="text" id="nova_mensagem" value={mensagem} onChange={(e) => setMensagem(e.target.value)} />
              <button onClick={enviarMensagem}>
                <FontAwesomeIcon icon={faPaperPlane} />
              </button>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default Contacts;
