import React, { FormEvent, useState } from 'react';
import Input from './Input';
import { cadastrarGrupo } from '../../services/GrupoService';
import { Grupo } from '../../interfaces/Grupo';

interface FormularioGrupoProps {
  usuarioId: number;
  cssClass?: string;
  setSeusGrupos: React.Dispatch<React.SetStateAction<Grupo[]>>;
  setShowFormularioGrupo: React.Dispatch<React.SetStateAction<boolean>>;
}

const FormularioGrupo: React.FC<FormularioGrupoProps> = ({ cssClass, usuarioId, setSeusGrupos, setShowFormularioGrupo }) => {
  const [nome, setNome] = useState('');

  const criarGrupo = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!nome) {
      alert('Nome é obrigatório');
      return;
    }

    const resGrupo = await cadastrarGrupo(nome, usuarioId);

    if (resGrupo.error) {
      alert(resGrupo.message);
      return;
    }

    setSeusGrupos((prevGrupos) => [...prevGrupos, resGrupo]);
    setShowFormularioGrupo(false);
  };

  return (
    <form className={`form_padrao form_grupo  ${cssClass && cssClass}`} onSubmit={criarGrupo}>
      <Input label="" placeholder="Nome do grupo..." tipo="text" obrigatorio={true} setValor={setNome} valor={nome} />
      <input type="submit" value="Criar" />
    </form>
  );
};

export default FormularioGrupo;
