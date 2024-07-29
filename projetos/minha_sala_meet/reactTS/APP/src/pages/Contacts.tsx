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
import { inserirMensagem, listarMensagensPorGrupo } from '../services/MensagemService';

const socketIO = socket;

const Contacts = () => {
  const [grupoSelecionado, setGrupoSelecionado] = useState<Grupo>();
  const [seusGrupos, setSeusGrupos] = useState<Grupo[]>();
  const [mensagem, setMensagem] = useState('');
  const [conversas, setConversas] = useState<Mensagem[]>([]);
  const [showConversa, setShowConversa] = useState(false);

  const auth = useContext(AuthContext);

  useEffect(() => {
    const handleMensagem = (data: Mensagem) => {
      setConversas((prevConversas) => [...prevConversas, data]);
    };

    socketIO.on('receberMensagem', handleMensagem);

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

    const res = await listarMensagensPorGrupo(id);

    if (res.error) {
      alert(res.error.message);
    }

    const mensagens: Mensagem[] = res;

    if (grupo) {
      setGrupoSelecionado(grupo);
      entrarSala(socketIO, grupo.uuid);

      // implementar lógica de mensagens antigas
      setConversas(mensagens);
      setShowConversa(true);
    } else {
      alert('Grupo não encontrado');
    }
  };

  const handleInputMensagem = async (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      await enviarMensagem();
    }
  };

  const enviarMensagem = async () => {
    // console.log(mensagem);

    if (mensagem === '') {
      return;
    }

    const novaMensagem: Mensagem = {
      data: new Date(),
      grupo: grupoSelecionado!,
      usuario: auth.user!,
      mensagem,
    };

    const res = await inserirMensagem(novaMensagem);

    if (res.error) {
      alert(res.message);
      return;
    }
    const ids = conversas.map((conversa) => conversa.id).filter((id) => id !== undefined) as number[];
    const maiorId = ids.length > 0 ? Math.max(...ids) : undefined;

    novaMensagem.id = maiorId! + 1;
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
                  <>
                    {conversa.grupo.id === grupoSelecionado!.id! && (
                      <div key={conversa.id} className="card_msg_pai">
                        {conversa.usuario.id === auth.user!.id! ? (
                          <CardMensagem key={`user-${conversa.id}`} conversa={conversa} tipoMsg="msg_user" />
                        ) : (
                          <div className="card_msg_integrante" key={`integrante-${conversa.id}`}>
                            <span>{conversa.usuario.nome}</span>
                            <CardMensagem key={`msg-${conversa.id}`} conversa={conversa} tipoMsg="msg_integrante" />
                          </div>
                        )}
                      </div>
                    )}
                  </>
                ))}
            </div>
            <div className="nova_mensagem_container">
              <input type="text" id="nova_mensagem" value={mensagem} onChange={(e) => setMensagem(e.target.value)} onKeyDown={handleInputMensagem} />
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
