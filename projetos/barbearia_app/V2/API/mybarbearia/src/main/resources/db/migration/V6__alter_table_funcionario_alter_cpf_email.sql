ALTER TABLE funcionario
ADD CONSTRAINT unique_email UNIQUE (email),
ADD CONSTRAINT unique_cpf UNIQUE (cpf);
