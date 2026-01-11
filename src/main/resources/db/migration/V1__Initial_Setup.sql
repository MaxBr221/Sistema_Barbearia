USE sistema_barbearia;

DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS barbeiro;
DROP TABLE IF EXISTS agendamento;
DROP TABLE IF EXISTS servico;
DROP TABLE IF EXISTS administrador;

CREATE TABLE cliente(
	id varchar(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    telefone varchar(10),
    email varchar(100),
    senha varchar(50));
    
CREATE TABLE barbeiro(
	id varchar(36) PRIMARY KEY,
    nome varchar(100) NOT NULL,
    telefone varchar(10),
    email varchar(100),
    senha varchar(50));
    
CREATE TABLE servico(
	id varchar(36) PRIMARY KEY,
    nome varchar(100) NOT NULL,
    duracao TIME NOT NULL);
    
    
CREATE TABLE agendamento(
	id varchar(36) PRIMARY KEY,
    dat DATE NOT NULL,
    hora TIME NOT NULL,
    barbeiro_id varchar(36),
    cliente_id varchar(36),
    servico_id varchar(36),
    status ENUM('DISPONIVEL','RESERVADO', 'CANCELADO') DEFAULT 'DISPONIVEL',
    tipo_servico ENUM('CORTE_CABELO', 'BARBA', 'SOBRANCELHA', 'PINTAR_CABELO'),
    
    FOREIGN KEY (barbeiro_id) REFERENCES barbeiro(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    FOREIGN KEY (servico_id) REFERENCES servico(id));
    
    
CREATE TABLE administrador(
	id varchar(36) PRIMARY KEY,
    nome varchar(100) NOT NULL,
    telefone varchar(10),
    email varchar(100),
    senha varchar(50));
