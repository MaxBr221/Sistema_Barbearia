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

    public void criarAgendamento(Context ctx){
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());

        String strData = ctx.formParam("data");
        String strHora = ctx.formParam("hora");
        String barbeiroId = ctx.formParam("barbeiroId");
        String clienteId = ctx.formParam("clienteId");
        String tipoServico = ctx.formParam("TipoServico");


        if (strData == null || strData.isBlank() || strHora == null || strHora.isBlank() || tipoServico == null || tipoServico.isBlank() || clienteId == null || clienteId.isBlank() || barbeiroId == null || barbeiroId.isBlank()){
            throw new IllegalArgumentException("Valores nulo/vazio.");
        }

        LocalDate data = LocalDate.parse(strData);
        LocalTime hora = LocalTime.parse(strHora);
        UUID barbeiro = UUID.fromString(barbeiroId);
        UUID cliente = UUID.fromString(clienteId);


        Barbeiro barbeiro1 = barbeiroService.buscarPorId(barbeiro);
        Cliente cliente1 = clienteService.buscarPorId(cliente);

        TipoServico tipoServico1 = TipoServico.valueOf(tipoServico);

        Agendamento age = new Agendamento(
                UUID.randomUUID(),
                data,
                hora,
                barbeiro1,
                cliente1,
                Status.RESERVADO,
                tipoServico1);
        try{
            agendamentoService.criar(age);
            logger.info("Agendamento criado com sucesso!");
            ctx.result("Agendado com sucesso!");
        }catch (IllegalArgumentException e){
            ctx.status(400).result(e.getMessage());
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
