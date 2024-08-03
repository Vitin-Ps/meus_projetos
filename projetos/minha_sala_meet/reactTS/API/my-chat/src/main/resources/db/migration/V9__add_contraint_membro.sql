ALTER TABLE tbl_membro
ADD CONSTRAINT unique_grupo_id_user_id UNIQUE (user_id, grupo_id);
