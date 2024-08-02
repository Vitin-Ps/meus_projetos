
import '../../css/animations.css';
import '../../css/loading_circle.css';

const Loading = () => {
  return (
    <div className='box_spinner_container'>
       <div className="box_spinner">
        <svg>
            <circle id="progress" name="circleProgress" cx="55" cy="55" r="74"></circle>
            <linearGradient id="gradient_padrao" x1="8.70032" y1="134.03" x2="183.959" y2="58.6294"
                gradientUnits="userSpaceOnUse">
                <stop stop-color="var(--cor004)" />
                <stop offset="1" stop-color="var(--cor002)" />
            </linearGradient>
        </svg>
        <div className="conteudo">
            <h2 className="texto-gradiente">Carregando</h2>
        </div>
    </div> 
    </div>
  )
}

export default Loading