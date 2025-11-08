package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.AdministradorService;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);


    public void mostrarPaginaLogin(Context ctx){
        logger.info("Direcionado para tela de login");
        ctx.render("login.html");
    }

    public void processarLogin(Context ctx){
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());

        Cliente cliente = clienteService.buscarPorLogin(login);
        if (cliente == null){
            logger.info("cliente " + cliente + " não está cadastrado.");
            ctx.render("/cadastro.html");
        }



    }
    public void logOut(Context ctx){

    }

}
