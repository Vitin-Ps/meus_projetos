create table vendas(
    idFunc int primary key auto-increment,
    nomeFunc varchar(80) not null,
    valorVenda decimal(10,2) not null,
    desconto decimal(10,2)
);