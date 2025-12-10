package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.ClienteService;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class ClienteController {
    private static final Logger logger = LogManager.getLogger(ClienteController.class);

    public void cadastrarCliente(Context ctx){
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());

        if(nome == null || nome.isBlank() || telefone == null || telefone.isBlank() || login == null || login.isBlank() || senha == null || senha.isBlank()){
            logger.warn("Não é permitido valores vazios.");
            ctx.attribute("Erro, não é possivel cadastrar usuário com campo vazio.");
            ctx.render("cadastro.html");
        }

        Cliente cliente = clienteService.buscarPorLogin(login);
        if (cliente != null){
            logger.info("Cliente: " + login + " já está cadastrado!");
            ctx.attribute("Erro", "Cliente já cadastrado!");
            ctx.render("login.html");
        }
        Cliente cliente1 = new Cliente(nome, telefone, login, senha);

        clienteService.cadastrarCliente(cliente1);
        logger.info("cliente " + nome + " cadastrado com sucesso!");
        ctx.attribute("Muito bem", "Cliente cadastrado com sucesso!");
        ctx.redirect("/telaCliente.html");
    }
    public void listarClientes(Context ctx){
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        List<Cliente> listaClientes = clienteService.listarClientes();
        logger.info("Listando clientes..");
        ctx.attribute("listaClientes", listaClientes);
        ctx.render("/listaClientes", Map.of("clientes", listaClientes));

    }
    public void editarCliente(Context ctx){
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        String idstr = ctx.formParam("id");

        try {
            UUID id = UUID.fromString(idstr);
            Cliente cliente = clienteService.buscarPorId(id);
            if (cliente == null){
                ctx.status(400).result("Cliente nulo.");
            }
            String nome = ctx.formParam("nome");
            String telefone = ctx.formParam("telefone");
            String login = ctx.formParam("login");
            String senha = ctx.formParam("senha");

            cliente.setNome(nome);
            cliente.setTelefone(telefone);
            cliente.setLogin(login);
            cliente.setSenha(senha);

            clienteService.editarCliente(cliente);
            logger.info("Cliente atualizado com sucesso!");
            ctx.status(200).result("Cliena atualizado com sucesso!");

        }catch (IllegalArgumentException e){
            logger.warn("id não coincidente com cliente" + e.getMessage());
            ctx.status(500).result("Cliente incorreto");
        }
    }
}
