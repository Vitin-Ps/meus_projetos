let imagens = [
    {
        id: 1,
        alt: 'Imagem 1',
        url: 'https://get.pxhere.com/photo/disney-mickey-mouse-castillo-walt-disney-world-sky-amusement-park-landmark-world-night-castle-park-architecture-recreation-nonbuilding-structure-water-castle-space-vehicle-tourist-attraction-evening-1592179.jpg'
    },
    {
        id: 2,
        alt: 'Imagem 2',
        url: 'https://imagens.usp.br/wp-content/uploads/Ciclovia-da-cidade-Universitaria_Foto-Marcos-Santos_U0Y3367-2048x1365.jpg'
    },
    {
        id: 3,
        alt: 'Imagem 3',
        url: 'https://c.pxhere.com/photos/6c/e0/blue_clouds_day_fluffy_sky_summer_nature-1248502.jpg!d'
    },
    {
        id: 4,
        alt: 'Imagem 4',
        url: 'https://get.pxhere.com/photo/disney-mickey-mouse-castillo-walt-disney-world-sky-amusement-park-landmark-world-night-castle-park-architecture-recreation-nonbuilding-structure-water-castle-space-vehicle-tourist-attraction-evening-1592179.jpg'
    },
    {
        id: 5,
        alt: 'Imagem 5',
        url: 'https://imagens.usp.br/wp-content/uploads/Ciclovia-da-cidade-Universitaria_Foto-Marcos-Santos_U0Y3367-2048x1365.jpg'
    },
    {
        id: 6,
        alt: 'Imagem 6',
        url: 'https://c.pxhere.com/photos/6c/e0/blue_clouds_day_fluffy_sky_summer_nature-1248502.jpg!d'
    },
]

var slider = document.querySelector(".slider");
var slides = document.querySelector(".slides");
// var radiosContainer = document.querySelector(".radio-btns-container");
var navigationAuto = document.querySelector(".navigation-auto");
var manualNavigation = document.querySelector(".manual-navigation");

var setaDireita = document.querySelector(".seta_direita");
var setaEsquerda = document.querySelector(".seta_esquerda");
var tamanhoTela = document.querySelector(".tamanho_tela");

const carregarImagens = () => {

    imagens.forEach((imagem) => {

        //imagens
        const slide = document.createElement('div');
        slide.classList.add('slide');

        const img = document.createElement('img');
        img.src = imagem.url;
        img.alt = imagem.alt;

        slide.appendChild(img);
        slides.appendChild(slide);


        //radios
        // const radio = document.createElement('input');
        // radio.type = 'radio';
        // radio.name = 'radio-btn';
        // radio.id = `radio_${imagem.id}`;

        // radiosContainer.appendChild(radio);

        // navigatio auto
        const btnAuto = document.createElement('div');
        btnAuto.classList.add(`auto-btn_${imagem.id}`);       

        navigationAuto.appendChild(btnAuto)

        // navigation manual
        const labelManual = document.createElement('div');
        labelManual.id = `radio_${imagem.id}`;
        labelManual.classList.add('manual-btn');
        labelManual.addEventListener('click', () => {
            const id = labelManual.id.split('_')[1];
            selecionarImagem(id);
        })

        manualNavigation.appendChild(labelManual);
    });

    const btnAuto = navigationAuto.querySelectorAll('*')[0];
    btnAuto.style.backgroundColor = '#FFF'
}

const selecionarImagem = (id) => {    
    const btnsAuto = navigationAuto.querySelectorAll('*');
    const btnAuto = Array.from(btnsAuto).find((btnAuto) => btnAuto.className ===`auto-btn_${id}`);

    btnsAuto.forEach((btnAuto) => {
        btnAuto.style.backgroundColor = 'transparent';
    });

    btnAuto.style.backgroundColor = '#FFF'

    slides.style.transform = `translateX(${-(id -1) * 100}%)`;
}

const mudarTelaCheia = () => {
    const imgs = slides.querySelectorAll('*');

    if(!tamanhoTelaStatus) {
        imgs.forEach((img) => {
            img.style = "width: 100%; height: 100%;";
        })
    
        slider.style = "width: 100%; height: 100%; transition: 1s; position: absolute; z-index: 15;";
        navigationAuto.style = "width: 100%;";
        manualNavigation.style = "width: 100%;";
    } else {
        imgs.forEach((img) => {
            img.style = "width: 500px; height: 500px;";
        })
    
        slider.style = "width: 500px; height: 500px;";
        navigationAuto.style = "width: 500px;";
        manualNavigation.style = "width: 500px;";
    }

    tamanhoTelaStatus = !tamanhoTelaStatus;
    
}

setaDireita.addEventListener('click', () => {
    if(count > imagens.length - 1) {
        count = 1;
    } else {
        count++;
    }

    selecionarImagem(imagens[count - 1].id);
})

setaEsquerda.addEventListener('click', () => {
    if(count === 1) {
        count = imagens.length;
    } else {
        count--;
    }

    selecionarImagem(imagens[count - 1].id);
});

tamanhoTela.addEventListener('click', () => {
    mudarTelaCheia();
});

carregarImagens();

let count = 1;
let tamanhoTelaStatus = false;

// document.getElementById("radio1").checked = true;

// setInterval(() => {
//     nextImage();
// }, 2000);


function nextImage() {
    count++;
    if(count > 6) {
        count = 1;
    }

    document.getElementById(`radio${count}`).checked = true;
}