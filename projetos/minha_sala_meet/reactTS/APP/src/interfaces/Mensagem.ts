import { Grupo } from './Grupo';
import { Usuario } from './Usuario';

// Interface Mensagem
export interface Mensagem {
  id?: number;
  grupo: Grupo;
  usuario: Usuario;
  mensagem: string;
  data: Date;
}

export interface MensagemJSON {
  grupo_id: number;
  usuario_id: number;
  mensagem: string;
  data: string;
}
