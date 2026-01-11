USE sistema_barbearia;

-- Increase password column size to handle BCrypt hashes (60 chars)
ALTER TABLE cliente MODIFY senha VARCHAR(255);
ALTER TABLE barbeiro MODIFY senha VARCHAR(255);
ALTER TABLE administrador MODIFY senha VARCHAR(255);
