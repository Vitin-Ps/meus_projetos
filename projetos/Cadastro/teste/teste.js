class Calculo {
    nome01;

    setNome(nome) {
        this.nome01 = nome;
    }
}

const com = new Calculo();

com.setNome("Victor");

console.log(com.nome01);