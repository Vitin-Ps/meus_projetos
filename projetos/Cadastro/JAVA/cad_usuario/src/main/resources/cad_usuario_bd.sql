DROP DATABASE IF EXISTS cad_vendas;

CREATE DATABASE IF NOT EXISTS cad_vendas;

USE cad_vendas;

CREATE TABLE funcionarios (
    idFunc BIGINT PRIMARY KEY AUTO_INCREMENT,
    nomeFunc VARCHAR(80) NOT NULL,
    matricula VARCHAR(80) NOT NULL UNIQUE,
    numero VARCHAR(20) NOT NULL,
    email VARCHAR(80) NOT NULL,
    descontoFunc INT NOT NULL
);


CREATE TABLE vendas (
    idVenda BIGINT PRIMARY KEY AUTO_INCREMENT,
    nomeVenda VARCHAR(80) NOT NULL,
    valorVenda DOUBLE(10,2) NOT NULL,
    descontoVenda DOUBLE(10,2)
);


-- SET @porcentagemDesconto = 10;
-- SET @desconto = @porcentagemDesconto * 0.01;

-- SELECT @desconto;
-- SELECT @porcentagemDesconto;

DELIMITER //

CREATE TRIGGER inserir_desconto
BEFORE INSERT ON vendas
FOR EACH ROW
BEGIN
    SET NEW.descontoVenda = NEW.valorVenda * (@porcentagemDesconto * 0.01);
END //

DELIMITER ;


