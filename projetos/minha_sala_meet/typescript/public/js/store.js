import * as constants from './constants.js';

let state = {
  socketId: null,
  localStream: null,
  remoteStream: null,
  screenSharingActive: false,
  screenSharingStream: null,
  permitirConexoesAleatorias: false,
  callState: constants.estadoLigacao.LIGACAO_DISPONIVEL_SOMENTE_CHAT,
};

export const setSocketId = (socketId) => {
  state = {
    ...state,
    socketId,
  };
};

export const setLocalStream = (stream) => {
  state = {
    ...state,
    localStream: stream,
  };
};

export const setPermitirConexoesAleatorias = (permitirConexao) => {
  state = {
    ...state,
    permitirConexoesAleatorias: permitirConexao,
  };
};

export const setScreenSharingActive = (screenSharingActive) => {
  state = {
    ...state,
    screenSharingActive: screenSharingActive,
  };
};

export const setScreenSharingStream = (screenSharingStream) => {
  state = {
    ...state,
    screenSharingStream: screenSharingStream,
  };
};

export const setRemoteStream = (remoteStream) => {
  state = {
    ...state,
    remoteStream: remoteStream,
  };
};

export const setCallState = (callState) => {
  state = {
    ...state,
    callState,
  };
};

export const getState = () => {
  return state;
};
