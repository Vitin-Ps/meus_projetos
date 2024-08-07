import { Mensagem } from '../interfaces/Mensagem';
import { Solicitacao } from '../interfaces/Solicitacao';
import { Amigo } from '../interfaces/Amigo';
import { Grupo } from '../interfaces/Grupo';
import { socket } from './socket';
import { Usuario } from '../interfaces/Usuario';
import { ConversaTipos } from '../interfaces/Conversa';

// ------------- RECIVE -----------------

export const recebeConversaEvent = (
  setConversas: React.Dispatch<React.SetStateAction<ConversaTipos[]>>,
  setMembros: React.Dispatch<React.SetStateAction<Usuario[]>>,
  setConversaSelecionada: React.Dispatch<React.SetStateAction<ConversaTipos | null>>,
) => {
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
      setConversaSelecionada(null);
    }
  });
};

export const receberNotificacao = (
  setNotificacoes: React.Dispatch<React.SetStateAction<Solicitacao[]>>,
  setCountNotificacao: React.Dispatch<React.SetStateAction<number>>,
  countNotificacao: number,
) => {
  socket.on('receber-notificacao', (notificacao) => {
    setNotificacoes((prevNotificacoes) => {
      if (!prevNotificacoes.includes(notificacao)) {
        return [...prevNotificacoes, notificacao];
      }
      return prevNotificacoes;
    });
    setCountNotificacao(countNotificacao + 1);
  });
};

export const receberMensagem = (setMensagens: React.Dispatch<React.SetStateAction<Mensagem[]>>) => {
  socket.on('receberMensagem', (mensagem: Mensagem) => {
    setMensagens((prevConversas) => {
      if (!prevConversas.includes(mensagem)) {
        return [...prevConversas, mensagem];
      }
      return prevConversas;
    });
  });
};

export const conectarUserAoSocketId = (user_id: number) => {
  socket.emit('conectar', user_id);
};

// ------------- EMITS --------------------

export const addCodigoUser = (codigo: string) => {
  socket.emit('addCodigoUser', codigo);
};

export const addMensagem = (data: Mensagem) => {
  socket.emit('addMensagem', data);
};

export const entrarSalas = (conversas: ConversaTipos[]) => {
  socket.emit('entrarSala', conversas);
};

export const enviarNotificacao = (notificacao: Solicitacao) => {
  socket.emit('enviar-notificacao', notificacao);
};

export const amigoEvent = (type: string, amigo: Amigo) => {
  socket.emit('amigo-event', {
    type,
    amigo,
  });
};

export const conversaEvent = (uuid: string, conversa: ConversaTipos, type: string, user?: Usuario) => {
  socket.emit('conversa-event', {
    uuid,
    conversa,
    type,
    user,
  });
};
