ALTER TABLE tbl_usuario ADD COLUMN ativo BOOLEAN NULL;

UPDATE tbl_usuario SET ativo = TRUE;
