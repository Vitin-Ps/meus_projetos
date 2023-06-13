DROP DATABASE IF EXISTS cad_vendas;

CREATE DATABASE IF NOT EXISTS cad_vendas;

USE cad_vendas;

CREATE TABLE funcionarios (
    id_func BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_func VARCHAR(80) NOT NULL,
    matricula VARCHAR(80) NOT NULL UNIQUE,
    numero VARCHAR(20) NOT NULL,
    email VARCHAR(80) NOT NULL,
    desconto_func INT NOT NULL
);


CREATE TABLE vendas (
    id_venda BIGINT PRIMARY KEY AUTO_INCREMENT,
    funcionario VARCHAR(80) NOT NULL,
    valor_venda DOUBLE(10,2) NOT NULL,
    desconto_venda DOUBLE(10,2)
);


-- SET @porcentagemDesconto = ?;
-- SET @desconto = @porcentagemDesconto * 0.01;

-- SELECT @desconto;
-- SELECT @porcentagemDesconto;

DELIMITER //

CREATE TRIGGER inserir_desconto
BEFORE INSERT ON vendas
FOR EACH ROW
BEGIN
    SET NEW.desconto_venda = NEW.valor_venda * (@porcentagemDesconto * 0.01);
END //

DELIMITER ;


