import * as wss from './wss.js';
import * as constants from './constants.js';
import * as ui from './ui.js';
import * as store from './store.js';

let userDetailsConectado;
let peerConexao;

export const enviarPedidoChamada = (ligacaoTipo, cod_unico_ligacao) => {
  userDetailsConectado = {
    ligacaoTipo,
    socketId: cod_unico_ligacao,
  };

  if (ligacaoTipo === constants.ligacaoTipo.CHAT_COD_UNICO || ligacaoTipo === constants.ligacaoTipo.VIDEO_COD_UNICO) {
    const data = {
      ligacaoTipo,
      cod_unico_ligacao,
    };
    ui.mostrarLigacaoMensagem(rejeitaLigacaoMensagem)
    wss.enviarPedidoChamada(data);
  }
};

export const executandoPedidoChamada = (data) => {
  const { ligacaoTipo, ligacaosocketId } = data;

  userDetailsConectado = {
    socketId: ligacaosocketId,
    ligacaoTipo,
  };

  if (ligacaoTipo === constants.ligacaoTipo.CHAT_COD_UNICO || ligacaoTipo === constants.ligacaoTipo.VIDEO_COD_UNICO) {
    console.log('Mostrando ligação Mensagem');
    ui.mostrarEntradaLigacaoChamada(ligacaoTipo, acaitarLigacao, rejeitarLigacao);
  }
};

const acaitarLigacao = () => {
  console.log('ligacao aceita...');
};

const rejeitarLigacao = () => {
  console.log("Ligação rejeitada...")
};

const rejeitaLigacaoMensagem = () => {
  console.log('Rejeitando a ligação...')
}
