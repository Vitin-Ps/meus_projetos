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
import { detalharGrupo } from '../services/GrupoService';
import CardConversa from './components/CardConversa';
import { inserirMensagem, listarMensagensPorGrupo } from '../services/MensagemService';
import FormularioGrupo from './components/FormularioGrupo';
import InfoConversa from './components/InfoConversa';
import Loading from './components/Loading';
import AmigosElement from './components/AmigosElement';
import { Solicitacao } from '../interfaces/Solicitacao';
import Input from './components/Input';
import { removerAcentuacoes } from '../services/FuncionalidadesService';
import { socket } from '../services/socket';
import { addMensagem, entrarSala } from '../services/wss';
import { Usuario } from '../interfaces/Usuario';
import { detalhaConversaPorUserId, listarConversasPorUserId } from '../services/ConversaService';
import { Conversa, ConversaTipos } from '../interfaces/Conversa';

const Contacts = () => {
  const [conversaSelecionada, setConversaSelecionada] = useState<ConversaTipos>();
  const [mensagem, setMensagem] = useState('');

  const [conversas, setConversas] = useState<ConversaTipos[]>([]);
  const [conversasAll, setConversasAll] = useState<ConversaTipos[]>([]);
  const [mensagens, setMensagens] = useState<Mensagem[]>([]);
  const [countNotificacao, setCountNotificacao] = useState<number>(0);
  const [notificacoes, setNotificacoes] = useState<Solicitacao[]>([]);
  const [membros, setMembros] = useState<Usuario[]>([]);

  const [showMensagem, setShowMensagem] = useState(false);
  const [showInfoUser, setShowInfoUser] = useState(false);
  const [showFormularioGrupo, setShowFormularioGrupo] = useState(false);
  const [showInfoConversa, setShowInfoConversa] = useState(false);
  const [showLoading, setShowLoading] = useState(false);

  const auth = useContext(AuthContext);

  useEffect(() => {
    socket.emit('conectar', auth.user!.id!);

    // socket.on('teste-chegou', (data) => {
    //   console.log('Received teste-chegou event:', data);
    // });

    socket.on('receberMensagem', (mensagem: Mensagem) => {
      setMensagens((prevConversas) => {
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

    socket.on('receber-conversa-event', (data) => {
      if (data.type === 'add') {
        setConversas((prevConversas) => {
          if (!prevConversas.includes(data.conversa)) {
            return [...prevConversas, data.conversa];
          }
          return prevConversas;
        });
      } else if (data.user && data.type === 'remover-membro') {
        setMembros((prevMembros) => prevMembros.filter((membro) => membro.id !== data.user.id!));
      } else {
        setConversas((prevConversas) => prevConversas.filter((conversa) => conversa.id !== data.conversa.id));
        setConversaSelecionada(undefined);
      }
    });

    const carregaDados = async () => {
      const resConversas = await listarConversasPorUserId(auth.user!.id!);

      if (resConversas.error) {
        alert(resConversas.message);
        return;
      }

      setConversas(resConversas);
      setConversasAll(resConversas);
      setShowLoading(true);
    };
    carregaDados();
  }, []);

  const entrarConversa = async (id: number) => {
    setShowLoading(false);
    const resConversa = await detalhaConversaPorUserId(auth.user!.id!, id);

    if (resConversa.error) {
      alert(resConversa.message);
      return;
    }

    const res = await listarMensagensPorGrupo(resConversa.id!);

    if (res.error) {
      alert(res.message);
      return;
    }

    if (resConversa) {
      setConversaSelecionada(resConversa);
      if (resConversa.tipo === 'GRUPO') {
        entrarSala(resConversa.grupo.conversa.uuid);
      } else {
        entrarSala(resConversa.privado.conversa.uuid);
      }

      // implementar lógica de mensagens antigas
      setMensagens(res);
      setShowMensagem(true);
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
      conversa: conversaSelecionada!.grupo ? conversaSelecionada?.grupo!.conversa! : conversaSelecionada?.privado!.conversa!,
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
    setMensagens((prevMensagens) => [...prevMensagens, novaMensagem]);
    setMensagem('');
  };

  const pesquisaGrupo = (pesquisa: string) => {
    const valor = removerAcentuacoes(pesquisa);
    setConversas(
      conversasAll.filter((conversa) => {
        const nome = removerAcentuacoes(conversa.grupo ? conversa.grupo.nome! : conversa.privado?.userTwo.nome!);
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
              setConversas={setConversas}
              setShowFormularioGrupo={setShowFormularioGrupo}
            />
            <Input tipo="text" placeholder="Pesquisar grupos..." onInput={(valor) => pesquisaGrupo(valor)} />
            <div className="grupos_container">
              {conversas &&
                conversas.map((conversa) => (
                  <CardConversa
                    key={conversa.id}
                    entrarConversa={entrarConversa}
                    nome={conversa.grupo ? conversa.grupo.nome! : conversa.privado?.userTwo.nome!}
                    id={conversa.id!}
                  />
                ))}
            </div>
          </div>
        </div>
        <div className="mensagens_container scroll-bar">
          <aside>
            {conversaSelecionada && (
              <div className="card_avatar">
                <img src="./images/avatar.jpg" alt="avatar" onClick={() => setShowInfoConversa(true)} />
                <h2>{conversaSelecionada.grupo ? conversaSelecionada.grupo.nome! : conversaSelecionada.privado?.userTwo.nome!}</h2>
              </div>
            )}
          </aside>
          <div className="mensagens_main_container">
            {conversaSelecionada && (
              <InfoConversa
                setShowInfoConversa={setShowInfoConversa}
                showInfoConversa={showInfoConversa}
                conversa={conversaSelecionada}
                user={auth.user!}
                setMembros={setMembros}
                membros={membros}
              />
            )}
            <div className="conteudo_container">
              {showMensagem &&
                mensagens.length > 0 &&
                mensagens.map(
                  (mensagem) =>
                    mensagem.conversa.id === conversaSelecionada!.id! && (
                      <div key={mensagem.id} className="card_msg_pai">
                        {mensagem.userRemetente.id === auth.user!.id! ? (
                          <CardMensagem conversa={mensagem} tipoMsg="msg_user" />
                        ) : (
                          <div className="card_msg_integrante">
                            <span>{mensagem.userRemetente.nome}</span>
                            <CardMensagem key={`msg-${mensagem.id}`} conversa={mensagem} tipoMsg="msg_integrante" />
                          </div>
                        )}
                      </div>
                    ),
                )}
            </div>
            {conversaSelecionada && (
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
