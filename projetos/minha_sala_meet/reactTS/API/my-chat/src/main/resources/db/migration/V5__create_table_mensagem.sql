
CREATE TABLE tbl_mensagem (
    id SERIAL PRIMARY KEY,
    conversa_id BIGINT NOT NULL,
    user_remetente BIGINT NOT NULL,
    mensagem VARCHAR(255) NOT NULL,
    data TIMESTAMP NOT NULL,

    CONSTRAINT fk_tbl_mensagem_conversa_id FOREIGN KEY (conversa_id) REFERENCES tbl_conversa(id),
    CONSTRAINT fk_tbl_mensagem_user_remetente FOREIGN KEY (user_remetente) REFERENCES tbl_usuario(id)
);