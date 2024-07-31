import { Grupo } from './Grupo';
import { Usuario } from './Usuario';

export interface ListaMembros {
  id?: number;
  grupo_id?: string;
  usuario_id?: string;
  grupo?: Grupo;
  usuario?: Usuario;
  cargo?: string;
}
