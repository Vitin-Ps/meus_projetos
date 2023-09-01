CREATE TABLE atendimento (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_cliente BIGINT NOT NULL,
    id_funcionario BIGINT NOT NULL,
    data DATETIME NOT NULL,
    duracao VARCHAR(10) NOT NULL,
    preco_total DECIMAL(10, 2) NOT NULL,


    CONSTRAINT fk_cliente_atendimento_id FOREIGN KEY(id_cliente) REFERENCES cliente(id) ON DELETE CASCADE,
    CONSTRAINT fk_funcionario_atendimento_id FOREIGN KEY(id_funcionario) REFERENCES funcionario(id) ON DELETE CASCADE
);



