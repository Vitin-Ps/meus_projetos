import '../css/Contacts.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import { Mensagem } from '../interfaces/Mensagem';
import { addCodigoUser, addMensagem, entrarSala } from '../services/wss';
import socket from '../services/socket';
import CardMensagem from './components/CardMensagem';

const cod_sala: string = 'dfghjjjj';
const conversas2: Mensagem[] = [
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
      nome: 'Marcos',
    },
    mensagem: 'JONAS FOI A PRAIA',
    data_hora: new Date(),
  },
  {
    id: 6,
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
    id: 7,
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
    id: 8,
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
  const [nomeGrupo, setNomeGrupo] = useState('Nenhum');
  const [idUser, setIdUser] = useState<number>();
  const [mensagem, setMensagem] = useState('');
  const [conversas, setConversas] = useState<Mensagem[]>(conversas2);
  const [showConversa, setShowConversa] = useState(false);

  // useEffect(() => {
  //   // socket.on('receberMensagem', (data: Mensagem) => {
  //   //   console.log('A mensagem chegou: ', data.mensagem);
  //   //   setConversas((prevConversas) => [...prevConversas, data]);
  //   // });
  // }, []);

  const entrarGrupo = () => {
    setNomeGrupo('Teste Grupo 1');
    entrarSala(socketIO, cod_sala);
    setShowConversa(true);
  };

  const entrarUser = (id: string) => {
    setIdUser(Number(id));
    socketIO.on('receberMensagem', (data: Mensagem) => {
      console.log('A mensagem chegou: ', data.mensagem);
      setConversas((prevConversas) => [...prevConversas, data]);
    });
    addCodigoUser(socket, id);
  };

  const enviarMensagem = () => {
    console.log(mensagem);

    const novaMensagem: Mensagem = {
      id: conversas.length + 1,
      data_hora: new Date(),
      grupo: {
        id: 5,
        uuid: 'dfghjjjj',
        nome: 'Os brabo da progamação',
      },
      usuario_remetente: {
        id: idUser as number,
        nome: 'Victin',
      },
      mensagem,
    };

    addMensagem(socketIO, novaMensagem);
    setConversas([...conversas, novaMensagem]);
    setMensagem('');
  };

  const teste = () => {
    const novaMensagem: Mensagem = {
      id: conversas.length + 1,
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
    };

    setConversas([...conversas, novaMensagem]);
    console.log(conversas);
  };

  return (
    <>
      <section className="contatos_container">
        <div className="conversas_container">
          <aside>
            <h2>Conversas</h2>
            {/* <button
              onClick={() => {
                entrarUser('1');
              }}
            >
              user 01
            </button>
            <button
              onClick={() => {
                entrarUser('2');
              }}
            >
              user 02
            </button>
            <button onClick={teste}>teste</button> */}
          </aside>
          <div className="conversas_main_container">
            <input type="text" placeholder="Pesquisar grupos" className="input_pesquisar_grupos" />
            <div className="grupos_container">
              <div className="card_conversa" onClick={entrarGrupo}>
                <img src="./images/avatar.jpg" alt="avatar" />
                <h2>Fulano</h2>
              </div>
            </div>
          </div>
        </div>
        <div className="mensagens_container">
          <aside>
            <div className="card_avatar">
              <img src="./images/avatar.jpg" alt="avatar" />
              <h2>{nomeGrupo}</h2>
            </div>
          </aside>
          <div className="mensagens_main_container">
            <div className="conteudo_container">
              {showConversa &&
                conversas.length > 0 &&
                conversas.map((conversa) => (
                  <div key={conversa.id} className="card_msg_pai">
                    {conversa.usuario_remetente.id === idUser ? (
                      <CardMensagem key={conversa.id} conversa={conversa} tipoMsg="msg_user" />
                    ) : (
                      <div className="card_msg_integrante" key={conversa.id}>
                        <span>{conversa.usuario_remetente.nome}</span>
                        <CardMensagem key={conversa.id} conversa={conversa} tipoMsg="msg_integrante" />
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
