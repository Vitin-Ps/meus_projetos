CREATE TABLE tbl_usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    imagem VARCHAR(255) NULL,
    ativo BOOLEAN NULL,
    tipo_usuario CHAR(8) NOT NULL
);
