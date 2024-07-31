import { Conversa } from './Conversa';
import { Grupo } from './Grupo';
import { Usuario } from './Usuario';

// Interface Mensagem
export interface Mensagem {
  id?: number;
  conversa: Conversa;
  userRemetente: Usuario;
  mensagem: string;
  data: Date;
}