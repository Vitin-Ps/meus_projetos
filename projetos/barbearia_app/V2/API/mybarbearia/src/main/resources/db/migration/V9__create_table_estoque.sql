CREATE TABLE carrinho_de_compras (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_item BIGINT NOT NULL,
    tipo_item VARCHAR(9) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL
);