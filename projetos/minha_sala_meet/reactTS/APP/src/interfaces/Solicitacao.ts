import { Usuario } from './Usuario';

export interface Solicitacao {
  id: number;
  userRemetente: Usuario;
  userDestinatario: Usuario;
}
