import '../css/Contacts.css';
import '../css/animations.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMinus, faPaperPlane, faPlus } from '@fortawesome/free-solid-svg-icons';
import { useContext, useEffect, useState } from 'react';
import { Mensagem } from '../interfaces/Mensagem';
import CardMensagem from './components/CardMensagem';
import Dasborad from './components/Dasborad';
import { AuthContext } from '../contexts/Auth/AuthContext';
import { Grupo } from '../interfaces/Grupo';
import { detalharGrupo, listarGruposPorUser } from '../services/GrupoService';
import CardConversa from './components/CardConversa';
import { inserirMensagem, listarMensagensPorGrupo } from '../services/MensagemService';
import FormularioGrupo from './components/FormularioGrupo';
import InfoGrupo from './components/InfoGrupo';
import Loading from './components/Loading';
import AmigosElement from './components/AmigosElement';
import { Solicitacao } from '../interfaces/Solicitacao';
import Input from './components/Input';
import { removerAcentuacoes } from '../services/FuncionalidadesService';
import { socket } from '../services/socket';
import { addMensagem, entrarSala } from '../services/wss';

const Contacts = () => {
  const [grupoSelecionado, setGrupoSelecionado] = useState<Grupo>();
  const [mensagem, setMensagem] = useState('');

  const [seusGrupos, setSeusGrupos] = useState<Grupo[]>([]);
  const [seusGruposAll, setSeusGruposAll] = useState<Grupo[]>([]);
  const [conversas, setConversas] = useState<Mensagem[]>([]);
  const [countNotificacao, setCountNotificacao] = useState<number>(0);
  const [notificacoes, setNotificacoes] = useState<Solicitacao[]>([]);

  const [showConversa, setShowConversa] = useState(false);
  const [showInfoUser, setShowInfoUser] = useState(false);
  const [showFormularioGrupo, setShowFormularioGrupo] = useState(false);
  const [showInfoGrupo, setShowInfoGrupo] = useState(false);
  const [showLoading, setShowLoading] = useState(false);

  const auth = useContext(AuthContext);

  useEffect(() => {
    socket.emit('conectar', auth.user!.id!);

    // socket.on('teste-chegou', (data) => {
    //   console.log('Received teste-chegou event:', data);
    // });

    socket.on('receberMensagem', (mensagem: Mensagem) => {
      setConversas((prevConversas) => {
        if (!prevConversas.includes(mensagem)) {
          return [...prevConversas, mensagem];
        }
        return prevConversas;
      });
    });
    socket.on('receber-notificacao', (notificacao) => {
      setNotificacoes((prevNotificacoes) => {
        if (!prevNotificacoes.includes(notificacao)) {
          return [...prevNotificacoes, notificacao];
        }
        return prevNotificacoes;
      });
      setCountNotificacao(countNotificacao + 1);
    });

    socket.on('receber-grupo-event', (data) => {
      console.log('chegou');
      if (data.type === 'add') {
        setSeusGrupos((prevSeusGrupos) => {
          if (!prevSeusGrupos.includes(data.grupo)) {
            return [...prevSeusGrupos, data.grupo];
          }
          return prevSeusGrupos;
        });
      } else {
        setSeusGrupos((prevSeusGrupos) => prevSeusGrupos.filter((grupo) => grupo.id !== data.grupo.id));
        setGrupoSelecionado(undefined);
      }
    });

    const carregaDados = async () => {
      const grupos = await listarGruposPorUser(auth.user!.id!);

      if (grupos.error) {
        alert(grupos.message);
        return;
      }

      setSeusGrupos(grupos);
      setSeusGruposAll(grupos);
      setShowLoading(true);
    };
    carregaDados();   
  }, []);

  const entrarGrupo = async (id: number) => {
    setShowLoading(false);
    const grupo: Grupo = await detalharGrupo(id);

    const res = await listarMensagensPorGrupo(grupo.conversa.id!);

    if (res.error) {
      alert(res.message);
      return;
    }

    const mensagens: Mensagem[] = res;

    if (grupo) {
      setGrupoSelecionado(grupo);
      entrarSala(grupo.conversa.uuid);

      // implementar lógica de mensagens antigas
      setConversas(mensagens);
      setShowConversa(true);
    } else {
      alert('Grupo não encontrado');
    }
    setShowLoading(true);
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
      conversa: grupoSelecionado!.conversa,
      userRemetente: auth.user!,
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
    addMensagem(novaMensagem);
    setConversas([...conversas, novaMensagem]);
    setMensagem('');
  };

  const pesquisaGrupo = (pesquisa: string) => {
    const valor = removerAcentuacoes(pesquisa);
    setSeusGrupos(
      seusGruposAll.filter((grupo) => {
        const nome = removerAcentuacoes(grupo.nome);
        return nome.includes(valor);
      }),
    );
  };

  return (
    <>
      {!showLoading && <Loading />}
      <Dasborad nome={auth.user?.nome!} setShowInfoUser={setShowInfoUser} showInfoUser={showInfoUser} countNotificacao={countNotificacao} />

      <section className="contatos_container">
        <AmigosElement
          showInfoUser={showInfoUser}
          user={auth.user!}
          setCountNotificacao={setCountNotificacao}
          countNotificacao={countNotificacao}
          notificacoes={notificacoes}
          setNotificacoes={setNotificacoes}
        />
        <div className="conversas_container">
          <aside>
            <h2>Conversas</h2>
            <button onClick={() => setShowFormularioGrupo(!showFormularioGrupo)}>
              {showFormularioGrupo ? <FontAwesomeIcon icon={faMinus} /> : <FontAwesomeIcon icon={faPlus} />}
            </button>
          </aside>
          <div className="conversas_main_container">
            <FormularioGrupo
              cssClass={showFormularioGrupo ? 'expand' : 'collapse'}
              usuarioId={auth.user!.id!}
              setSeusGrupos={setSeusGrupos}
              setShowFormularioGrupo={setShowFormularioGrupo}
            />
            <Input tipo="text" placeholder="Pesquisar grupos..." onInput={(valor) => pesquisaGrupo(valor)} />
            <div className="grupos_container">
              {seusGrupos && seusGrupos.map((grupo) => <CardConversa key={grupo.id} entrarGrupo={entrarGrupo} nome={grupo.nome} id={grupo.id!} />)}
            </div>
          </div>
        </div>
        <div className="mensagens_container scroll-bar">
          <aside>
            {grupoSelecionado && (
              <div className="card_avatar">
                <img src="./images/avatar.jpg" alt="avatar" onClick={() => setShowInfoGrupo(true)} />
                <h2>{grupoSelecionado.nome}</h2>
              </div>
            )}
          </aside>
          <div className="mensagens_main_container">
            <InfoGrupo setShowInfoGrupo={setShowInfoGrupo} showInfoGrupo={showInfoGrupo} grupo={grupoSelecionado!} userId={auth.user!.id!} />
            <div className="conteudo_container">
              {showConversa &&
                conversas.length > 0 &&
                conversas.map(
                  (conversa) =>
                    conversa.conversa.id === grupoSelecionado!.conversa.id! && (
                      <div key={conversa.id} className="card_msg_pai">
                        {conversa.userRemetente.id === auth.user!.id! ? (
                          <CardMensagem conversa={conversa} tipoMsg="msg_user" />
                        ) : (
                          <div className="card_msg_integrante">
                            <span>{conversa.userRemetente.nome}</span>
                            <CardMensagem key={`msg-${conversa.id}`} conversa={conversa} tipoMsg="msg_integrante" />
                          </div>
                        )}
                      </div>
                    ),
                )}
            </div>
            {grupoSelecionado && (
              <div className="nova_mensagem_container">
                <input
                  type="text"
                  id="nova_mensagem"
                  value={mensagem}
                  onChange={(e) => setMensagem(e.target.value)}
                  onKeyDown={handleInputMensagem}
                />
                <button onClick={enviarMensagem}>
                  <FontAwesomeIcon icon={faPaperPlane} />
                </button>
              </div>
            )}
          </div>
        </div>
      </section>
    </>
  );
};

export default Contacts;
