CREATE TABLE cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefone CHAR(14) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    cep CHAR(8) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    numero CHAR(5) NOT NULL,
    complemento VARCHAR(200) NULL,
    uf CHAR(2) NOT NULL,
    ativo TINYINT NOT NULL
);



