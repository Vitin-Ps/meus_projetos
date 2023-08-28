ALTER TABLE servico ADD ativo TINYINT NULL;

UPDATE servico SET ativo = 1;