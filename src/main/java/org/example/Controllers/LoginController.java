package org.example.Controllers;

import io.javalin.http.Context;
import ognl.EnumerationIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {
    private static final Logger logger = LogManager.getLogger(LoginController.class);


    public void mostrarPaginaLogin(Context ctx){
        logger.info("Direcionado para tela de login");
        ctx.render("login.html");
    }
    public void mostrarPaginaCliente(Context ctx){
        Cliente cliente = ctx.sessionAttribute("cliente");

        if(cliente == null){
            ctx.redirect("/login");
            return;
        }
        logger.info("Direcionando para tela do cliente");
        ctx.attribute("clienteId", cliente.getId().toString());
        ctx.attribute("cliente", cliente);
        ctx.render("telaCliente");
    }

    public void processarLogin(Context ctx){
        String login = ctx.formParam("login");
        String senha = ctx.formParam("senha");

        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());

        Cliente cliente = clienteService.buscarPorLogin(login);
        if (cliente != null){
            if (BCrypt.checkpw(senha, cliente.getSenha())) {
                logger.info("Tentativa de login valida com sucesso!");
                ctx.sessionAttribute("cliente", cliente);
                ctx.redirect("/telaCliente");
                return;
            }else {
                logger.warn("senha invalida para login.");
                ctx.attribute("Erro, login ou senha invalida.");
                ctx.render("login");

            }
        }
        Barbeiro barbeiro = barbeiroService.buscarPorLogin(login);
        if (barbeiro != null){
            if (BCrypt.checkpw(senha, barbeiro.getSenha())){
                logger.info("Tentativa de login valida com sucesso!");
                ctx.sessionAttribute("barbeiro", barbeiro);
                ctx.redirect("barbeiro.html");
                return;
            }else {
                logger.warn("Tentativa de login com erro.");
                ctx.attribute("Erro, senha ou login incorreto!");
                ctx.render("login.html");
            }

        }
        logger.warn(login + " não está cadastrado.");
        ctx.attribute("Erro", "usuario não está cadastrado!");
        ctx.render("login");
    }
    public void logOut(Context ctx){
        ctx.attribute("usuario", null);
        logger.info("Direcionando para saida..");
        ctx.redirect("/login");

    }

}
