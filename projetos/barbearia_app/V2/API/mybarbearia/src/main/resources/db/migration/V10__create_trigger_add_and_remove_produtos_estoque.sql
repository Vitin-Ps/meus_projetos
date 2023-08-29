DELIMITER //
CREATE TRIGGER add_produto_estoque
AFTER INSERT ON produto
FOR EACH ROW
BEGIN
    INSERT INTO estoque (id_produto, quantidade) VALUES (NEW.id, 0);
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER remove_produto_estoque
AFTER DELETE ON produto
FOR EACH ROW
BEGIN
    DELETE FROM estoque WHERE id_produto = OLD.id;
END;
//
DELIMITER ;
