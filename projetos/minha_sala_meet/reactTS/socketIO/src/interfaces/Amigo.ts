import { Usuario } from './Usuario';

export interface Amigo {
  id: number;
  user: Usuario;
  amigo: Usuario;
}
