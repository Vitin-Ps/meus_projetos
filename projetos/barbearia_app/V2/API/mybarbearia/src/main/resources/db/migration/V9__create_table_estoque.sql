CREATE TABLE estoque (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_produto BIGINT NOT NULL,
    quantidade INT NULL,

    CONSTRAINT fk_produto_estoque_id FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE
);
