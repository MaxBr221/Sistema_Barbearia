-- Criação da tabela Cliente
CREATE TABLE IF NOT EXISTS Cliente (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Criação da tabela Barbeiro
CREATE TABLE IF NOT EXISTS Barbeiro (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    login VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

-- Criação da tabela Agendamento (conforme README)
CREATE TABLE IF NOT EXISTS Agendamento (
    id VARCHAR(36) PRIMARY KEY,
    data DATE NOT NULL,
    hora TIME NOT NULL,
    barbeiro_id VARCHAR(36),
    cliente_id VARCHAR(36),
    status ENUM('DISPONIVEL','RESERVADO','CANCELADO') DEFAULT 'DISPONIVEL',
    tipo_servico ENUM('CORTE_CABELO','BARBA','SOBRANCELHA'),
    FOREIGN KEY (barbeiro_id) REFERENCES Barbeiro(id),
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);
