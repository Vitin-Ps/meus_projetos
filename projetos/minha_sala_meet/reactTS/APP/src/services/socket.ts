// src/socket.ts
import { io } from 'socket.io-client';

const SOCKET_SERVER_URL = process.env.REACT_APP_SOCKETIO || 'http://localhost:3005';
// const SOCKET_SERVER_URL = "http://localhost:3005";
export const socket = io(SOCKET_SERVER_URL, {
  transports: ['websocket', 'polling'],
});
