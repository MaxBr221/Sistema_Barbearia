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

    public void mostrarPaginaAgendamento(Context ctx) {
        String clienteId = ctx.pathParam("clienteId");
        ctx.attribute("clienteId", clienteId);
        logger.info("Direcionando para tela de agendamento");

        String msg = ctx.sessionAttribute("msg");
        String msgErro = ctx.sessionAttribute("msg_erro");

        if (msg != null) {
            ctx.attribute("msg", msg);
            ctx.sessionAttribute("msg", null);
        }
        if (msgErro != null) {
            ctx.attribute("msg_erro", msgErro);
            ctx.sessionAttribute("msg_erro", null);
        }
        String nulo = ctx.consumeSessionAttribute("nulo");
        ctx.attribute("nulo", nulo);
        ctx.render("novoAgendamento");
        String ocupado = ctx.consumeSessionAttribute("ocupado");
        ctx.attribute("ocupado", ocupado);
        ctx.render("novoAgendamento");
    }

    public void criarAgendamento(Context ctx) {
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());

        logger.info("No metodo criarAgendamento");

        String strData = ctx.formParam("data");
        String strHora = ctx.formParam("hora");
        String clienteId = ctx.formParam("clienteId");
        String tipoServicoStr = ctx.formParam("servicos");

        try {

            if (strData == null || strHora == null || clienteId == null || tipoServicoStr == null ||
                    strData.isBlank() || strHora.isBlank() || clienteId.isBlank()) {
                ctx.sessionAttribute("nulo", "É necessário escolher ao menos um serviço para prosseguir!");
                ctx.redirect("/novoAgendamento/" + clienteId);
                return;
            }

            LocalDate data = LocalDate.parse(strData);
            LocalTime hora = LocalTime.parse(strHora);
            UUID clienteUUID = UUID.fromString(clienteId);

            agendamentoService.validarData(data);

            if (agendamentoService.existeAgendamento(data, hora)) {
                logger.warn("Horario ocupado!");
                ctx.sessionAttribute("ocupado", "Esse horário já está ocupado!");
                ctx.redirect("/novoAgendamento/" + clienteId);
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
                    tipoServico);
            UUID id = agendamento.getCliente().getId();
            agendamentoService.criar(agendamento);
            logger.info("Agendamento criado com sucesso!");
            ctx.sessionAttribute("msg", "Agendamento criado com sucesso!");
            ctx.redirect("/novoAgendamento/" + id);
        } catch (Exception e) {
            logger.error("Erro ao criar agendamento", e);
            ctx.sessionAttribute("msg_erro", e.getMessage());
            ctx.redirect("/novoAgendamento/" + clienteId);
        }
    }

    public void listarAgendamentos(Context ctx) {
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        String strId = ctx.pathParam("clienteId");
        UUID id = UUID.fromString(strId);
        List<Agendamento> listaAgendamentos = agendamentoService.listarAgendamentosAtivos(id);
        ctx.attribute("agendamentos", listaAgendamentos);
        ctx.render("meusAgendamentos");
        logger.info("Listando agendamentos..");

    }

    public void listarHistoricoDeAgendamento(Context ctx) {
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        String strId = ctx.pathParam("clienteId");
        UUID id = UUID.fromString(strId);
        List<Agendamento> listarHistorico = agendamentoService.listarHistorico(id);
        ctx.attribute("historicos", listarHistorico);
        ctx.render("historico");
        logger.info("listando histórico");
    }

    public void removerAgendamento(@NotNull Context ctx) {
        logger.info("No metodo remover Agendamento");
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        String strId = ctx.pathParam("id");
        UUID id = UUID.fromString(strId);
        Agendamento agendamentos = agendamentoService.buscarAgendamentoPorId(id);
        if (agendamentos == null) {
            logger.warn("Agendamento não encontrado: " + id);
            ctx.status(404);
            ctx.result("Agendamento não encontrado.");
            return;
        }
        if (agendamentos.getStatus().equals(Status.RESERVADO)) {
            UUID clienteId = agendamentos.getCliente().getId();
            agendamentoService.removerAgendamento(id);
            logger.info("Agendamento removido com sucesso!");
            ctx.redirect("/meusAgendamentos/" + clienteId);
        } else {
            logger.info("Esse agendamento não está agendado");
            ctx.result("Esse agendamento não existe.");
        }
    }
}
