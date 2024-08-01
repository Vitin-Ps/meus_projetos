import React from 'react';

interface InputProps {
  label?: string;
  tipo: string;
  placeholder: string;
  setValor?: (valor: string) => void;
  onInput?: (valor: string) => void;
  valor?: any;
  obrigatorio?: boolean;
}

const Input: React.FC<InputProps> = ({ label, tipo, placeholder, setValor, valor, onInput, obrigatorio: obrigatório = false }) => {
  return (
    <div className="form-group">
      {label && <label>{label}</label>}
      <input
        type={tipo}
        placeholder={placeholder}
        onChange={(e) => setValor && setValor((e.target as HTMLInputElement).value)}
        onInput={(e) => onInput && onInput((e.target as HTMLInputElement).value)}
        required={obrigatório}
        value={valor}
      />
    </div>
  );
};

export default Input;
