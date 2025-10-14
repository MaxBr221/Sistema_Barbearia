Sistema de Barbearia
🔹 Descrição

Sistema web fullstack para gerenciamento de uma barbearia. Permite cadastro de clientes e barbeiros, controle de agendamentos, horários de atendimento e permissões de acesso.

Desenvolvido em Java com Javalin no backend, HTML/CSS no frontend e SQL para persistência de dados.

O sistema implementa controle de usuários por papel (ADMIN, BARBEIRO, CLIENTE), garantindo que cada usuário acesse apenas as funcionalidades permitidas.

🛠 Tecnologias

Backend: Java + Javalin

Frontend: HTML, CSS, Thymeleaf

Banco de dados: SQL (MySQL/PostgreSQL)

Controle de versão: Git + GitHub

⚙️ Funcionalidades
Usuários

Cadastro e login de clientes e barbeiros

Controle de permissões via Role (ADMIN, BARBEIRO, CLIENTE)

Clientes

Cadastro, edição, listagem e remoção

Visualização de agendamentos

Barbeiros

Gerenciamento da agenda de atendimentos

Consulta de clientes agendados

Agendamentos

Criar, listar e editar horários

Verificação automática de disponibilidade

Administração (ADMIN)

Gerenciar clientes, barbeiros e serviços

Controle completo do sistema e permissões

📌 Observações

O sistema usa Service + Repository:

Service: regras de negócio e validações

Repository: CRUD com SQL

Usuários ADMIN têm controle completo do sistema.

Usuários BARBEIRO podem gerenciar seus agendamentos e clientes.

Usuários CLIENTE podem agendar serviços e consultar seus horários.
