CREATE TABLE tbl_membro (
    id SERIAL PRIMARY KEY,
    grupo_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    cargo CHAR(8) NOT NULL,

    CONSTRAINT fk_tbl_membro_grupo_id FOREIGN KEY (grupo_id) REFERENCES tbl_grupo(id),
    CONSTRAINT fk_tbl_membro_user_id FOREIGN KEY (user_id) REFERENCES tbl_usuario(id)
);

CREATE TABLE tbl_amigos (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amigo_id BIGINT NOT NULL,

    CONSTRAINT fk_tbl_amigos_user_id FOREIGN KEY (user_id) REFERENCES tbl_usuario(id),
    CONSTRAINT fk_tbl_amigos_amigo_id FOREIGN KEY (amigo_id) REFERENCES tbl_usuario(id),
    CONSTRAINT unique_users_amigos UNIQUE (user_id, amigo_id)
);

CREATE TABLE tbl_solicitacao (
    id SERIAL PRIMARY KEY,
    user_remetente_id BIGINT NOT NULL,
    user_destinatario_id BIGINT NOT NULL,

    CONSTRAINT fk_tbl_tbl_solicitacao_user_remetente_id FOREIGN KEY (user_remetente_id) REFERENCES tbl_usuario(id),
    CONSTRAINT fk_tbl_tbl_solicitacao_user_destinatario_id FOREIGN KEY (user_destinatario_id) REFERENCES tbl_usuario(id),
    CONSTRAINT unique_users_solicitacao UNIQUE (user_remetente_id, user_destinatario_id)
);
