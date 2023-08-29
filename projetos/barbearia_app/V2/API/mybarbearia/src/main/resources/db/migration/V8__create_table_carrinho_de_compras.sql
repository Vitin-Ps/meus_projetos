CREATE TABLE carrinho_de_compras (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_produto BIGINT NULL,
    id_servico BIGINT NULL,
    preco DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_carrinho_cliente_id FOREIGN KEY (id_cliente) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_produto_carrinho_id FOREIGN KEY (id_produto) REFERENCES produto(id) ON DELETE CASCADE,
    CONSTRAINT fk_servico_carrinho_id FOREIGN KEY (id_servico) REFERENCES servico(id) ON DELETE CASCADE

);
