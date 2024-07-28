import { Link, Outlet } from 'react-router-dom';
import './css/App.css';
import NavBar from './pages/components/NavBar';

function App() {
  return (
    <div className="App">
      {/* <header>
        <aside>Meu Chat</aside>
        <NavBar />
      </header> */}

      <header className='header_estilo_new'>
       <p>Olá! Bem vindo Victor</p>
       <p>Sair</p>
      </header>

      <div className="main">
        <Outlet />
      </div>

      {/* <footer>
        <p>&copy;MailtoDesenvolper</p>
      </footer> */}
    </div>
  );
}

export default App;
