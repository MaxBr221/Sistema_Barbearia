USE sistema_barbearia;

-- Insert Default Barber 'Igor'
-- Password '123456'
-- IMPORTANT: Column is 'email', not 'login' (Aligned with V1)
INSERT INTO barbeiro (id, nome, telefone, email, senha) 
VALUES (
    'be0e6b6d-4c3d-4c3d-4c3d-be0e6b6d4c3d', 
    'Igor Barbeiro', 
    '11999999999', 
    'igor@barbearia.com', 
    '$2a$10$N.zmdr9k7uOCQb376NoUnutj8iAt6ValmpHsAw.0uRVOGgg0o.Wgm'
);

-- Insert Services corresponding to Enum types
INSERT INTO servico (id, nome, duracao) VALUES ('s1', 'CORTE_CABELO', '00:30:00');
INSERT INTO servico (id, nome, duracao) VALUES ('s2', 'BARBA', '00:20:00');
INSERT INTO servico (id, nome, duracao) VALUES ('s3', 'SOBRANCELHA', '00:15:00');
INSERT INTO servico (id, nome, duracao) VALUES ('s4', 'PINTAR_CABELO', '01:00:00');
