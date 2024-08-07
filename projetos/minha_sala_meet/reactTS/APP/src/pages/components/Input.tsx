import React, { Ref, RefObject } from 'react';

interface InputProps {
  label?: string;
  tipo: string;
  placeholder?: string;
  setValor?: (valor: string) => void;
  onInput?: (valor: string) => void;
  onKeyDown?: (valor: any) => void;
  inputRef?: RefObject<HTMLInputElement>;
  valor?: any;
  obrigatorio?: boolean;
}

const Input: React.FC<InputProps> = ({ label, tipo, placeholder, setValor, valor, onInput, onKeyDown, inputRef, obrigatorio = false }) => {
  return (
    <div className="form-group">
      {label && <label>{label}</label>}
      <input
        type={tipo}
        placeholder={placeholder}
        onChange={(e) => setValor && setValor((e.target as HTMLInputElement).value)}
        onInput={(e) => onInput && onInput((e.target as HTMLInputElement).value)}
        onKeyDown={(e) => onKeyDown && onKeyDown(e)}
        required={obrigatorio}
        value={valor}
        ref={inputRef}
      />
    </div>
  );
};

export default Input;
