import { Grupo } from './Grupo';
import { Privado } from './Privado';

export interface Conversa {
  id?: number;
  uuid: string;
}

export interface ConversaTipos {
  id?: number;
  grupo?: Grupo;
  privado?: Privado;
  tipo: string;
}
