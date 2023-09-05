CREATE TABLE historico (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_atendimento BIGINT NOT NULL,
    id_funcionario BIGINT NULL,
    valor_total DECIMAL(10, 2) NULL,
    comissao DECIMAL(10, 2) NULL,

    CONSTRAINT fk_funcionario_historico FOREIGN KEY(id_funcionario) REFERENCES funcionario(id) ON DELETE CASCADE,
    CONSTRAINT fk_atendimento_historico FOREIGN KEY(id_atendimento) REFERENCES atendimento(id) ON DELETE CASCADE
);