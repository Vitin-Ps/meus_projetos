ALTER TABLE tbl_usuario ADD ativo TINYINT NULL;

UPDATE tbl_usuario SET ativo = 1;