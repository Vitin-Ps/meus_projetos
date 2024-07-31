import { FormEvent, useContext, useState } from 'react';
import '../css/Login.css';
import Input from './components/Input';
import { Usuario } from '../interfaces/Usuario';
import { cadastrarUsuario } from '../services/UsuarioService';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../contexts/Auth/AuthContext';
import { getToken, removeToken } from '../services/TokenService';

const Login = () => {
  const [showCadastro, setShowCadastro] = useState(false);
  const [login, setLogin] = useState('');
  const [senha, setSenha] = useState('');
  const [nome, setNome] = useState('');
  const navigate = useNavigate();

  const auth = useContext(AuthContext);

  const cadastrarUser = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (nome === '' || login === '' || senha === '') {
      alert('Prencha todos os dados!');
    }

    const user: Usuario = {
      nome,
      login,
      senha,
    };

    const res = await cadastrarUsuario(user);
    if (!res) {
      console.log('Ocorreu algum erro no servior. Tente novamente mais tarde!');
      limparInputs();
      return;
    } else {
      alert('Usuário cadastrado com sucesso');
      navigate('/');
    }
  };

  const fazerLogin = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!login || !senha) {
      alert('Preencha todos os Campos');
    }

    if (getToken()) {
      removeToken();
    }

    const isLogged: boolean = await auth.signin(login, senha);

    if (isLogged) {
      navigate('/contacts');
    }
  };

  const limparInputs = () => {
    setNome('');
    setLogin('');
    setSenha('');
  };

  return (
    <>
      <div className="fundo_branco">
        {!showCadastro ? (
          <div className="entrar_container">
            <h2>Entrar</h2>
            <form className="form_padrao" onSubmit={fazerLogin}>
              <Input
                label="Login:"
                tipo="string"
                placeholder="Digite seu Login"
                setValor={(valor) => setLogin(valor)}
                obrigatorio={true}
                valor={login}
              />
              <Input
                label="Senha:"
                tipo="password"
                placeholder="Digite sua Senha"
                setValor={(valor) => setSenha(valor)}
                obrigatorio={true}
                valor={senha}
              />
              <div>
                <span>
                  Ainda não é cadastrado?{' '}
                  <span
                    className="link"
                    onClick={() => {
                      setShowCadastro(true);
                      limparInputs();
                    }}
                  >
                    Cadastre-se
                  </span>
                  !
                </span>
              </div>
              <input type="submit" value="Entrar" />
            </form>
          </div>
        ) : (
          <div className="cadastro_container">
            <h2>Cadastre-se</h2>
            <form className="form_padrao" onSubmit={cadastrarUser}>
              <Input label="Nome:" tipo="string" placeholder="Digite seu Nome" setValor={(valor) => setNome(valor)} obrigatorio={true} valor={nome} />
              <Input
                label="Login:"
                tipo="string"
                placeholder="Digite seu Login"
                setValor={(valor) => setLogin(valor)}
                obrigatorio={true}
                valor={login}
              />
              <Input
                label="Senha:"
                tipo="password"
                placeholder="Digite sua Senha"
                setValor={(valor) => setSenha(valor)}
                obrigatorio={true}
                valor={senha}
              />
              <div>
                <span>
                  Já tem conta?{' '}
                  <span
                    className="link"
                    onClick={() => {
                      {
                        setShowCadastro(false);
                        limparInputs();
                      }
                    }}
                  >
                    Entre
                  </span>
                  !
                </span>
              </div>
              <input type="submit" value="Criar" />
            </form>
          </div>
        )}
      </div>
    </>
  );
};

export default Login;
