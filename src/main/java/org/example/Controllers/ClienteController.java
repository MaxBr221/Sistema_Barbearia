package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.ClienteService;

public class ClienteController {
    private static final Logger logger = LogManager.getLogger(ClienteController.class);

    public void cadastrarCliente(Context ctx){
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());

        if(nome == null || telefone == null || login == null || senha == null){
            logger.warn("Não é permitido valores nulo.");
            ctx.attribute("Erro, não é possivel cadastrar cliente com campo nulo.");
            ctx.render("cadastro.html");
        }


        Cliente cliente = new Cliente();

        clienteService.cadastrarCliente(cliente);


    }

}
