USE sistema_barbearia;

-- Increase phone column size to handle full number with formatting or country codes
ALTER TABLE cliente MODIFY telefone VARCHAR(20);
ALTER TABLE barbeiro MODIFY telefone VARCHAR(20);
ALTER TABLE administrador MODIFY telefone VARCHAR(20);
