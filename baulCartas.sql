CREATE DATABASE  baulCartas;
USE baulCartas;

CREATE TABLE carta (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tipo_carta VARCHAR(20) NOT NULL, -- 'PARA_MIM' ou 'REFLEXAO'
    titulo VARCHAR(120) NOT NULL,
    destinatario VARCHAR(120) NOT NULL,
    mensagem TEXT NOT NULL,
    data_registro VARCHAR(20) NOT NULL,
    sentimento VARCHAR(25) NOT NULL,
    momento_vida VARCHAR(120),
    ler_novamente_em VARCHAR(120),
    gatilho_ou_cena VARCHAR(150),
    aprendizado TEXT
);	