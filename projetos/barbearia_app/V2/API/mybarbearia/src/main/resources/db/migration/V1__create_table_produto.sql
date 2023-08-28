CREATE TABLE produto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    marca VARCHAR(100) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    comissao DECIMAL(10, 2) NOT NULL
);
