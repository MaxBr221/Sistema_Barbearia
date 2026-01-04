💈 Sistema de Agendamento para Barbearia

Sistema web desenvolvido em Java, utilizando Javalin como framework web e Thymeleaf para renderização das interfaces.
O sistema gerencia agendamentos, clientes, barbeiros e serviços, com persistência via JDBC em banco de dados relacional.

Projeto desenvolvido com foco acadêmico e prático, servindo também como item de portfólio.

🚀 Funcionalidades

📅 Criar agendamentos com data, hora, cliente, barbeiro e tipo de serviço

📋 Listar agendamentos cadastrados

❌ Cancelar agendamentos

⏳ Não exibir agendamentos anteriores ao dia atual

👤 Cadastro de clientes

✂️ Cadastro de barbeiros

🧾 Uso de Enum para status do agendamento e tipo de serviço

💾 Persistência de dados com JDBC

🏗️ Arquitetura do Projeto

O projeto segue uma organização inspirada no padrão MVC, com separação clara de responsabilidades:

src
 ┣ controller
 ┃ └── Controladores das rotas Javalin
 ┣ service
 ┃ └── Regras de negócio
 ┣ repository
 ┃ └── Interfaces Repository
 ┣ BancoDeDados
 ┃ └── Implementações JDBC dos repositórios
 ┣ model
 ┃ └── Entidades do sistema
 ┣ enums
 ┃ └── Status e tipos de serviço
 ┣ util
 ┃ └── Conexão com banco e utilidades
 ┗ resources
   ┗── Templates Thymeleaf (HTML)

   
🛠️ Tecnologias Utilizadas

Java

Javalin

Thymeleaf

JDBC

MySQL

HTML e CSS

Maven

Git e GitHub

🗃️ Banco de Dados
Tabela agendamento
CREATE TABLE agendamento (
    id VARCHAR(36) PRIMARY KEY,
    dat DATE NOT NULL,
    hora TIME NOT NULL,
    barbeiro_id VARCHAR(36),
    cliente_id VARCHAR(36),
    status ENUM('DISPONIVEL','RESERVADO','CANCELADO') DEFAULT 'DISPONIVEL',
    tipo_servico ENUM('CORTE_CABELO','BARBA','SOBRANCELHA')
);

▶️ Como Executar o Projeto

Clone o repositório:

git clone https://github.com/seu-usuario/seu-repositorio.git


Configure o banco de dados MySQL

Crie o schema

Ajuste usuário e senha no arquivo de conexão JDBC

Compile o projeto:

mvn clean install


Execute a aplicação:

Pela IDE, executando a classe Main

Ou via terminal:

mvn exec:java


📚 Conceitos Aplicados

Arquitetura MVC

Separação de responsabilidades

JDBC na prática

Uso de Enums

Organização de pacotes

Versionamento com Git

👨‍💻 Autor

Desenvolvido por Maxsuel
Graduando em Sistemas de Informação
