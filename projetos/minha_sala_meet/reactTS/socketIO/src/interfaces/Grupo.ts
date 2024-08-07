import { Conversa } from './Conversa';

export interface Grupo {
  id?: number;
  nome: string;
  imagem?: string;
  conversa: Conversa;
}
