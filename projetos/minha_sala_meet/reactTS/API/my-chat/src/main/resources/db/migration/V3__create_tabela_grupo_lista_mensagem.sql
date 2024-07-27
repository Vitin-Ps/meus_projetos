CREATE TABLE tbl_grupo (
    id INT PRIMARY kEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    uuid VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tbl_lista (
    id INT PRIMARY kEY AUTO_INCREMENT,
    grupo_id INT NOT NULL,
    usuario_id INT NOT NULL,
    cargo CHAR(8) NOT NULL,

    CONSTRAINT fk_tbl_lista_id_grupo FOREIGN KEY(grupo_id) REFERENCES tbl_grupo(id),
    CONSTRAINT fk_tbl_lista_id_usuario FOREIGN KEY(usuario_id) REFERENCES tbl_usuario(id)
);

CREATE TABLE tbl_mensagem (
    id INT PRIMARY kEY AUTO_INCREMENT,
    grupo_id INT NOT NULL,
    usuario_id INT NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    data DATE NOT NULL,

    CONSTRAINT fk_tbl_mensagem_id_grupo FOREIGN KEY(grupo_id) REFERENCES tbl_grupo(id),
    CONSTRAINT fk_tbl_mensagem_id_usuario FOREIGN KEY(usuario_id) REFERENCES tbl_usuario(id)
);

