import React, { FormEvent, useState } from 'react';
import Input from './Input';
import { cadastrarGrupo } from '../../services/GrupoService';
import { addIntegranteGrupo } from '../../services/ListaMembrosService';

interface FormularioGrupoProps {
  usuarioId: number;
  cssClass?: string;
}

const FormularioGrupo: React.FC<FormularioGrupoProps> = ({ cssClass, usuarioId }) => {
  const [nome, setNome] = useState('');

  const criarGrupo = async (e: FormEvent<HTMLFormElement>) => {
    // e.preventDefault();

    if (!nome) {
      alert('Nome é obrigatório');
      return;
    }

    const resGrupo = await cadastrarGrupo(nome, usuarioId);

    if (resGrupo.error) {
      alert(resGrupo.message);
      return;
    }   
  };

  return (
    <form className={`form_padrao form_grupo  ${cssClass && cssClass}`} onSubmit={criarGrupo}>
      <Input label="" placeholder="Nome do grupo..." tipo="text" obrigatório={true} setValor={setNome} valor={nome} />
      <input type="submit" value="Criar" />
    </form>
  );
};

export default FormularioGrupo;
