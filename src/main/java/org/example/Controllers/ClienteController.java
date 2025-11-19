package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.ClienteService;

import java.util.List;
import java.util.Map;


public class ClienteController {
    private static final Logger logger = LogManager.getLogger(ClienteController.class);

    public void cadastrarCliente(Context ctx){
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());


        if(nome.isBlank() || telefone.isBlank() || login.isBlank() || senha.isBlank()){
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
    public Cliente editarCliente(Context ctx){


    }
}
