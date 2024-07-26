import { Outlet } from 'react-router-dom';
import './App.css';
import NavBar from './pages/NavBar';

function App() {
  return (
    <div className="App">
      <header>
        <aside>Meu Chat</aside>
        <NavBar />
      </header>

      <div className="main">
        <Outlet />
      </div>

      <footer>
        <p>&copy;MailtoDesenvolper</p>
      </footer>
    </div>
  );
}

export default App;
