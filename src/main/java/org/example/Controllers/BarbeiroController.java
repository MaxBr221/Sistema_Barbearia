package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.Agendamento;
import org.example.Dominios.Cliente;
import org.example.Keys;
import org.example.Services.AgendamentoService;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BarbeiroController {
    private static final Logger logger = LogManager.getLogger(BarbeiroController.class);


    public void listarCLientes(Context ctx){
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        List<Cliente> listarClientes = clienteService.listarClientes();
        logger.info("Listando clientes...");
        ctx.attribute("listarClientes", listarClientes);
        ctx.render("/listarClientes.html", Map.of("listarClientes", listarClientes));
    }

    public void listarAgendamentos(Context ctx){
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        List<Agendamento> listaAgendamentos = agendamentoService.listarAgendamento();
        logger.info("Listando agendamentos");
        ctx.attribute("listarAgendamento", listaAgendamentos);
    }

    public void removerCliente(@NotNull Context ctx){
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());
        String idStr = ctx.formParam("id");
        try {
            UUID id = UUID.fromString(idStr);
            Cliente cliente = clienteService.buscarPorId(id);
//            clienteService.removerCliente(cliente.getId());
            logger.info("Cliente removido com sucesso!");
        }
        catch (IllegalArgumentException e){
            logger.warn("Erro, id do cliente incorreto." + e.getMessage());
            ctx.status(400).result("id inválido!");
        }
    }
}
