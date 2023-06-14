DROP DATABASE IF EXISTS cad_vendas; -- se o database existir, exclua

CREATE DATABASE IF NOT EXISTS cad_vendas; -- se o database não existir, crie

USE cad_vendas; -- usar database

CREATE TABLE funcionarios (
    id_func BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome_func VARCHAR(80) NOT NULL,
    matricula VARCHAR(80) NOT NULL UNIQUE, -- unique significa que a matricula é unica, não pode ser repetida
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

-- Trigger para inserção
DELIMITER //

CREATE TRIGGER inserir_desconto
BEFORE INSERT ON vendas -- depois de inserir na tabela vendas
FOR EACH ROW
BEGIN
    IF NEW.desconto_venda IS NULL THEN -- se desconto_venda for null
        SET NEW.desconto_venda = NEW.valor_venda * (@porcentagemDesconto * 0.01); -- mudar desconto_venda baseado na variável alterada pelo backend
    END IF;
END //

DELIMITER ;


-- Trigger para atualização
DELIMITER //

CREATE TRIGGER atualizar_desconto -- mesma coisa só que para atualização
BEFORE UPDATE ON vendas
FOR EACH ROW
BEGIN
    IF NEW.desconto_venda IS NULL THEN
        SET NEW.desconto_venda = NEW.valor_venda * (@porcentagemDesconto * 0.01);
    END IF;
END //

DELIMITER ;

-- alteração de nome e desconto baseado na tabela funcionarios


DELIMITER //

CREATE TRIGGER atualizar_funcionario_vendas
BEFORE UPDATE ON funcionarios -- depois de atualizar a tabela funcionarios 
FOR EACH ROW
BEGIN
    UPDATE vendas
    SET funcionario = NEW.nome_func, -- mudar funcionario, baseado na alteração de funcionarios
		desconto_venda = vendas.valor_venda * (NEW.desconto_func * 0.01) -- mudar desconto_venda baseado na alteração de desconto_func
    WHERE funcionario = OLD.nome_func; -- com o parametro do mome que foi alterado, por isso OLD
END //

DELIMITER ;
