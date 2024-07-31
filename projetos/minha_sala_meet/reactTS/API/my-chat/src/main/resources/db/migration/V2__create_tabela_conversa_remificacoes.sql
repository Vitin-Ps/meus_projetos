CREATE TABLE tbl_conversa (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(255) NOT NULL UNIQUE,
    tipo CHAR(7) NOT NULL
);

CREATE TABLE tbl_grupo (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    uuid VARCHAR(255) NOT NULL UNIQUE,
    imagem VARCHAR(255) NOT NULL,
    conversa_id BIGINT NOT NULL,

    CONSTRAINT fl_tbl_grupo_conversa_id FOREIGN KEY (conversa_id) REFERENCES tbl_conversa(id)
);

CREATE TABLE tbl_privado (
    id SERIAL PRIMARY KEY,
    user_one_id BIGINT NOT NULL,
    user_two_id BIGINT NOT NULL,
    conversa_id BIGINT NOT NULL,

    CONSTRAINT fl_tbl_privado_user_one_id FOREIGN KEY (user_one_id) REFERENCES tbl_usuario(id),
    CONSTRAINT fl_tbl_privado_user_two_id FOREIGN KEY (user_two_id) REFERENCES tbl_usuario(id),
    CONSTRAINT fl_tbl_privado_conversa_id FOREIGN KEY (conversa_id) REFERENCES tbl_conversa(id),
    CONSTRAINT unique_users_conversa UNIQUE (user_one_id, user_two_id)
);