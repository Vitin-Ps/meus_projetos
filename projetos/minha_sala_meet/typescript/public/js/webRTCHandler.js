import * as wss from './wss.js';
import * as constants from './constants.js';
import * as ui from './ui.js';
import * as store from './store.js';

let userDetailsConectado;
let peerConexao;
let dataCanal;

const constraintsPadroes = {
  audio: true,
  video: true,
};

const configuracao = {
  iceServers: [
    {
      urls: 'stun:stun.l.google.com:19302',
    },
  ],
};

export const getPreviewLocal = () => {
  navigator.mediaDevices
    .getUserMedia(constraintsPadroes)
    .then((stream) => {
      ui.atualizaVideoLocal(stream);
      store.setLocalStream(stream);
    })
    .catch((erro) => {
      console.log('Ocorreu algum erro ao tentar ter acesso á câmera: ', erro);
    });
};

const criarPeerConexao = () => {
  peerConexao = new RTCPeerConnection(configuracao);

  // dataCanal = peerConexao.createDataChannel('chat');

  // peerConexao.ondatachannel = (event) => {
  //   const dataCanal = event.channel;

  //   dataCanal.onopen = () => {
  //     console.log(' Conexão peer está pronta para receber as mensagens dos dados do canal');
  //   };

  //   dataCanal.onmessage = (event) => {
  //     console.log('mensagem veio do data Canal');
  //     const mensagem = JSON.parse(event.data);
  //     // ui.inserirMensagem(mensagem)
  //     console.log(mensagem);
  //   };
  // };

  peerConexao.onicecandidate = (event) => {
    console.log('buscando ice candidates para criar a conexão de ponto a ponto');
    if (event.candidate) {
      // envia essa ice candidates para outro peer
      wss.enviarDadosUsandoSinalWebRTC({
        usuarioConectadoSocketId: userDetailsConectado.socketId,
        tipo: constants.sinalWebRTC.ICE_CANDIDATE,
        candidate: event.candidate,
      });
    }
  };

  peerConexao.onconnectionstatechange = (event) => {
    if (peerConexao.connectionState === 'connected') {
      console.log('A conexão com o outro peer foi um sucesso');
    }
  };

  // recebendo tracks(faixas)

  const streamRemoto = new MediaStream();
  store.setRemoteStream(streamRemoto);
  ui.atualizaVideoRemoto(streamRemoto);

  peerConexao.ontrack = (evento) => {
    streamRemoto.addTrack(evento.track);
  };

  // adicionar esse stream no peer de conexão
  if (userDetailsConectado.ligacaoTipo === constants.ligacaoTipo.VIDEO_COD_UNICO) {
    const streamLocal = store.getState().localStream;
    for (const track of streamLocal.getTracks()) {
      peerConexao.addTrack(track, streamLocal);
    }
  }
};

export const enviarPedidoChamada = (ligacaoTipo, cod_unico_ligacao) => {
  userDetailsConectado = {
    ligacaoTipo,
    socketId: cod_unico_ligacao,
  };

  if (ligacaoTipo === constants.ligacaoTipo.CHAT_COD_UNICO || ligacaoTipo === constants.ligacaoTipo.VIDEO_COD_UNICO) {
    const data = {
      ligacaoTipo,
      chamadorSocketId: cod_unico_ligacao,
    };
    ui.mostrarLigacaoMensagem(rejeitaLigacaoMensagem);
    wss.enviarPedidoChamada(data);
  }
};

export const executaPedidoChamada = (data) => {
  const { ligacaoTipo, chamadorSocketId } = data;

  userDetailsConectado = {
    socketId: chamadorSocketId,
    ligacaoTipo,
  };

  if (ligacaoTipo === constants.ligacaoTipo.CHAT_COD_UNICO || ligacaoTipo === constants.ligacaoTipo.VIDEO_COD_UNICO) {
    console.log('Mostrando ligação Mensagem');
    ui.mostrarEntradaLigacaoChamada(ligacaoTipo, aceitarLigacao, rejeitarLigacao);
  }
};

export const executaPedidoChamadaResposta = (data) => {
  const { pedidoChamadaResposta } = data;
  ui.removerAllAlertas();

  if (pedidoChamadaResposta === constants.pedidoChamadaResposta.CHAMADA_NAO_ENCONTRADA) {
    ui.mostrarInfoAlerta(pedidoChamadaResposta);
  }

  if (pedidoChamadaResposta === constants.pedidoChamadaResposta.CHAMADA_INDISPONIVEL) {
    ui.mostrarInfoAlerta(pedidoChamadaResposta);
  }

  if (pedidoChamadaResposta === constants.pedidoChamadaResposta.CHAMADA_REJEITADA) {
    ui.mostrarInfoAlerta(pedidoChamadaResposta);
  }

  if (pedidoChamadaResposta === constants.pedidoChamadaResposta.CHAMADA_ACEITA) {
    // enciar o webRtc pedido
    ui.mostrarLigacaoElementos(userDetailsConectado.ligacaoTipo);
    criarPeerConexao();

    enviarPedidoWebRTC();
  }
};

const aceitarLigacao = () => {
  console.log('ligacao aceita...');
  criarPeerConexao();
  enviarPedidoChamadaResposta(constants.pedidoChamadaResposta.CHAMADA_ACEITA);
  ui.mostrarLigacaoElementos(userDetailsConectado.ligacaoTipo);
};

const rejeitarLigacao = () => {
  console.log('Ligação rejeitada...');
  enviarPedidoChamadaResposta(constants.ligacaoTipo.CHAMADA_REJEITADA);
};

const rejeitaLigacaoMensagem = () => {
  console.log('Rejeitando a ligação...');
};

const enviarPedidoChamadaResposta = (pedidoChamadaResposta) => {
  const data = {
    chamadorSocketId: userDetailsConectado.socketId,
    pedidoChamadaResposta,
  };
  ui.removerAllAlertas();
  wss.enviarPedidoChamadaResposta(data);
};

export const enviarPedidoWebRTC = async () => {
  const pedido = await peerConexao.createOffer();
  await peerConexao.setLocalDescription(pedido);

  wss.enviarDadosUsandoSinalWebRTC({
    usuarioConectadoSocketId: userDetailsConectado.socketId,
    tipo: constants.sinalWebRTC.PEDIDO,
    pedido: pedido,
  });
};

export const executaWebRTCPedido = async (data) => {
  await peerConexao.setRemoteDescription(data.pedido);
  const resposta = await peerConexao.createAnswer();

  await peerConexao.setLocalDescription(resposta);
  wss.enviarDadosUsandoSinalWebRTC({
    usuarioConectadoSocketId: userDetailsConectado.socketId,
    tipo: constants.sinalWebRTC.RESPOSTA,
    resposta: resposta,
  });
};

export const executaWebRTCResposta = async (data) => {
  console.log('executando webRTC resposta');
  await peerConexao.setRemoteDescription(data.resposta);
};

export const executaWebRTCCandidate = async (data) => {
  console.log('executando entrada webRTC candidates');
  try {
    await peerConexao.addIceCandidate(data.candidate);
  } catch (erro) {
    console.error('ocorreu um erro ao tentar adicionar ice candidate recebido: ', erro);
  }
};

let compartilharTelaStream;

export const trocaEntreCameraETelaCompartilhada = async (telaCompatilhaAtivada) => {
  if (telaCompatilhaAtivada) {
    const streamLocal = store.getState().localStream;
    const senders = peerConexao.getSenders();

    const sender = senders.finmd((sender) => {
      return sender.track.kind === streamLocal.getVideoTracks()[0].kind;
    });

    if (sender) {
      sender.replaceTrack(streamLocal.getVideoTracks()[0]);
    }

    // parar de compartilhar o stream da tela

    store
      .getState()
      .screenSharingActive.getTracks()
      .forEach((track) => track.stop());

    store.setScreenSharingActive(!telaCompatilhaAtivada);

    ui.atualizarVideoLocal(streamLocal);
  } else {
    console.log('Trocando pelo compartilhamento de tela');

    try {
      compartilharTelaStream = await navigator.mediaDevices.getDisplayMedia({
        video: true,
      });

      store.setScreenSharingStream(compartilharTelaStream);

      // Substituir track pelo sender enviado
      const senders = peerConexao.getSenders();

      const sender = senders.finmd((sender) => {
        return sender.track.kind === streamLocal.getVideoTracks()[0].kind;
      });
  
      if (sender) {
        sender.replaceTrack(streamLocal.getVideoTracks()[0]);
      }     
  
      store.setScreenSharingActive(!telaCompatilhaAtivada);
  
      ui.atualizarVideoLocal(compartilharTelaStream);
    } catch (erro) {
      console.log('Ocorreu um erro ao tentar pegar o stream compartilhado');
    }
  }
};
