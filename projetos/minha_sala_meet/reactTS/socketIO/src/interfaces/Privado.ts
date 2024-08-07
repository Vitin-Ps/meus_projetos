import { Conversa } from "./Conversa";
import { Usuario } from "./Usuario";

export interface Privado {
  id?: number;
  userOne: Usuario;
  userTwo: Usuario;
  conversa: Conversa;
}
