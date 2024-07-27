// src/socket.ts
import { io } from 'socket.io-client';

const SOCKET_SERVER_URL = 'http://localhost:3005'; // Substitua pela URL do seu servidor Socket.IO
const socket = io(SOCKET_SERVER_URL);

export default socket;
