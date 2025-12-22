package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.*;
import org.example.Keys;
import org.example.Services.AgendamentoService;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class AgendamentoController {
    private static final Logger logger = LogManager.getLogger(AgendamentoController.class);

    public void mostrarPaginaAgendamento(Context ctx){
        String clienteId = ctx.pathParam("clienteId");
        ctx.attribute("clienteId", clienteId);
        logger.info("Direcionando para tela de agendamento");
        ctx.render("novoAgendamento.html");
    }

    public void criarAgendamento(Context ctx) {
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());

        logger.info("No metodo criarAgendamento");

        try {
            String strData = ctx.formParam("data");
            String strHora = ctx.formParam("hora");
            String clienteId = ctx.formParam("clienteId");
            String tipoServicoStr = ctx.formParam("servicos");

            if (strData == null || strHora == null || clienteId == null || tipoServicoStr == null ||
                    strData.isBlank() || strHora.isBlank() || clienteId.isBlank()) {
                ctx.status(400).result("Campos obrigatórios não preenchidos");
                return;
            }

            LocalDate data = LocalDate.parse(strData);
            LocalTime hora = LocalTime.parse(strHora);
            UUID clienteUUID = UUID.fromString(clienteId);

            if (agendamentoService.existeAgendamento(data, hora)) {
                ctx.status(400).result("Horário já ocupado");
                return;
            }

            Barbeiro barbeiro = barbeiroService.buscarBarbeiroPadrao();
            Cliente cliente = clienteService.buscarPorId(clienteUUID);
            TipoServico tipoServico = TipoServico.valueOf(tipoServicoStr);

            Agendamento agendamento = new Agendamento(
                    UUID.randomUUID(),
                    data,
                    hora,
                    barbeiro,
                    cliente,
                    Status.RESERVADO,
                    tipoServico
            );

            agendamentoService.criar(agendamento);
            ctx.result("Agendamento criado com sucesso!");

        } catch (Exception e) {
            logger.error("Erro ao criar agendamento", e);
            ctx.status(400).result("Erro ao criar agendamento");
        }
    }

    public void listarAgendamentos(Context ctx){
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        List<Agendamento> listaAgendamentos = agendamentoService.listarAgendamento();
        logger.info("Listando agendamentos..");
        ctx.attribute("listaAgendamentos", listaAgendamentos);
        ctx.render("listaDeAgendamentos");
    }
    public void removerAgendamento(@NotNull Context ctx){
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        String strId = ctx.formParam("id");
        if(strId == null || strId.isBlank()){
            throw new IllegalArgumentException("Não é permitido id nulo/vazio");
        }
        UUID id = UUID.fromString(strId);

        Agendamento agendamento = agendamentoService.buscarAgendamentoPorId(id);
        if(agendamento.getStatus().equals(Status.RESERVADO)){
            agendamentoService.removerAgendamento(agendamento.getId());
            logger.info("Agendamento removido com sucesso!");
            ctx.result("Agendamento removido com sucesso!");
        }else{
            logger.info("Esse agendamento não está agendado");
            ctx.result("Esse agendamento não existe.");
        }
    }
}
