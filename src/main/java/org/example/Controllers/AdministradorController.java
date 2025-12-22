package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.AdministradorService;

import java.util.List;
import java.util.Map;
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
            ctx.result("Erro, não é possivel cadastrar barbeiro com campo vazio.");
            ctx.render("cadastro.html");
        }
        Barbeiro barbeiro = administradorService.buscarBarbeiroPorLogin(login);
        if(barbeiro != null){
            logger.info("Barbeiro já existente!");
            ctx.result("Barbeiro já existente!");
            ctx.render("cadastro.html");
        }
        Barbeiro barbeiro1 = new Barbeiro(UUID.randomUUID(), nome, telefone, login, senha);
        logger.info("Barbeiro " + nome + " cadastrado com sucesso!");
        ctx.attribute("Barbeiro", "Barbeiro cadastrado com sucesso!");
        ctx.redirect("barbeiro.html");

    }
    public void cadastrarCliente(Context ctx){
        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
        String nome = ctx.formParam("nome");
        String telefone = ctx.formParam("telefone");
        String login = ctx.formParam("login");
        String  senha = ctx.formParam("senha");

        if(nome.isBlank() || telefone.isBlank() || login.isBlank() || senha.isBlank()){
            logger.warn("Não é permitido deixar espaços vazios.");
            ctx.result("Erro, não é possivel cadastrar cliente com campo vazio.");
            ctx.render("cadastro.html");
        }

        Cliente cliente = administradorService.buscarClientePorLogin(login);
        if (cliente != null){
            logger.info("Cliente já está cadastrado!");
            ctx.result("cliente já cadastrado no sistema!");
            ctx.render("cadastro.html");
        }

        Cliente cliente1 = new Cliente(UUID.randomUUID(), nome, telefone, login, senha);
        administradorService.cadastrarCliente(cliente1);
        logger.info(cliente1.getNome() + " cadastrado com sucesso!");
        ctx.result("Cliente cadastrado com sucesso!");
        ctx.redirect("cliente.html");

    }
//    public void removerbarbeiro(Context ctx){
//        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
//        String strId = ctx.formParam("id");
//        UUID id = UUID.fromString(strId);
//
//        Barbeiro barbeiro = administradorService.buscarBarbeiroPorId(id);
//
//        try{
//            if(barbeiro != null){
//                administradorService.removerBarbeiro(id);
//                logger.info("Barbeiro removido com sucesso!");
//                ctx.result("Barbeiro removido com sucesso!");
//                ctx.redirect("barbeiroTela.html");
//            }
//        }catch (IllegalArgumentException e){
//            logger.info("Barbeiro não cadastrado!");
//            ctx.status(400).result("Barbeiro não existente.");
//            ctx.render("usuarioNaoEncontrado.html");
//        }
//    }
//    public void removerCliente(Context ctx){
//        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
//        String strId = ctx.formParam("id");
//        UUID id = UUID.fromString(strId);
//
//        Cliente cliente = administradorService.buscarClientePorId(id);
//        try{
//            if(cliente != null) {
//                administradorService.removerCliente(cliente.getId());
//                logger.info("Cliente " + cliente.getNome() + " removido com sucesso!");
//                ctx.status(200).result("Cliente removido com sucesso!");
//                ctx.redirect("usuario.html");
//            }
//        }catch (IllegalArgumentException e){
//            logger.info("usuário não existente");
//            ctx.status(400).result("cliente não encontrado.");
//            ctx.render("usuarioNaoEncotrado.html");
//        }
//    }
//    public void listarBarbeiros(Context ctx){
//        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
//        List<Barbeiro> barbeiros = administradorService.listarBarbeiros();
//        logger.info("Listando barbeiros");
//        ctx.attribute("barbeiros", barbeiros);
//        ctx.render("barbeiros.html", Map.of("barbeiros", barbeiros));
//
//    }
    public void listarClientes(Context ctx){
        AdministradorService administradorService = ctx.appData(Keys.ADMINISTRADOR_SERVICE.key());
        List<Cliente> clientes = administradorService.listarClientes();
        logger.info("listando clientes");
        ctx.attribute("clientes", clientes);
        ctx.render("clientes", Map.of("clientes", clientes));
    }
}
