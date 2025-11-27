package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Barbeiro;
import org.example.Keys;
import org.example.Services.AdministradorService;

import java.util.UUID;

public class AdministradorController {
    private static final Logger logger = LogManager.getLogger(AdministradorController.class);

    public void cadastrarBarbeiro(Context ctx){
        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        if(nome.isBlank() || telefone.isBlank() || login.isBlank() || senha.isBlank()){
            logger.warn("Não é permitido valores vazios.");
            ctx.attribute("Erro, não é possivel cadastrar barbeiro com campo vazio.");
            ctx.render("cadastro.html");
        }
        Barbeiro barbeiro = administradorService.buscarBarbeiroPorLogin(login);
        if(barbeiro != null){
            logger.info("Barbeiro já existente!");
            ctx.result("Barbeiro já existente!");
            ctx.render("cadastro.html");
        }
        Barbeiro barbeiro1 = new Barbeiro(UUID.randomUUID(), nome, telefone, login, senha);
        administradorService.cadastrarBarbeiro(barbeiro1);
        logger.info("Barbeiro " + nome + " cadastrado com sucesso!");
        ctx.attribute("Barbeiro", "Barbeiro cadastrado com sucesso!");
        ctx.redirect("barbeiro.html");

    }

}
