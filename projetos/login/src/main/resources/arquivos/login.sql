DROP DATABASE IF EXISTS projeto_login;

CREATE DATABASE IF NOT EXISTS projeto_login;

USE projeto_login;

CREATE TABLE usuario (
    login VARCHAR(20) PRIMARY KEY NOT NULL,
    senha VARCHAR(30) NOT NULL
);
