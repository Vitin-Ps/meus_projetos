CREATE TABLE recibo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_atendimento BIGINT NOT NULL,
    id_produto BIGINT NULL,
    id_servico BIGINT NULL,
    quantidade INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_atendimento_recibo_id FOREIGN KEY(id_atendimento) REFERENCES atendimento(id) ON DELETE CASCADE,
    CONSTRAINT fk_produto_recibo_id FOREIGN KEY(id_produto) REFERENCES produto(id) ON DELETE CASCADE,
    CONSTRAINT fk_servico_recibo_id FOREIGN KEY(id_servico) REFERENCES servico(id) ON DELETE CASCADE
);

