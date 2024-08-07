import { User } from './User';

export interface Sala {
  id?: number;
  uuid: string;
  membros: User[];
  type: string
}
