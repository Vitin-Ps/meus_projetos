ALTER TABLE tbl_lista
ADD CONSTRAINT unique_grupo_usuario
UNIQUE (grupo_id, usuario_id);
