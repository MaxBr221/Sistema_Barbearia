package org.example.Controllers;

import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Dominios.*;
import org.example.Keys;
import org.example.Services.AgendamentoService;
import org.example.Services.BarbeiroService;
import org.example.Services.ClienteService;
import org.example.Services.ServicoService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AgendamentoController {
    private static final Logger logger = LogManager.getLogger(AgendamentoController.class);


    public void criarAgendamento(Context ctx){
        AgendamentoService agendamentoService = ctx.appData(Keys.AGENDAMENTO_SERVICE.key());
        BarbeiroService barbeiroService = ctx.appData(Keys.BARBEIRO_SERVICE.key());
        ClienteService clienteService = ctx.appData(Keys.CLIENTE_SERVICE.key());
        ServicoService servicoService = ctx.appData(Keys.SERVICO_SERVICE.key());

        String strData = ctx.formParam("data");
        String strHora = ctx.formParam("hora");
        String barbeiroId = ctx.formParam("barbeiroId");
        String clienteId = ctx.formParam("clienteId");
        String servicoId = ctx.formParam("servicoId");
        String tipoServico = ctx.formParam("TipoServico");


        if (strData == null || strData.isBlank() || strHora == null || strHora.isBlank() || tipoServico == null || tipoServico.isBlank() || clienteId == null || clienteId.isBlank() || barbeiroId == null || barbeiroId.isBlank() || servicoId == null || servicoId.isBlank()){
            throw new IllegalArgumentException("Valores nulo/vazio.");
        }

        LocalDate data = LocalDate.parse(strData);
        LocalTime hora = LocalTime.parse(strHora);
        UUID barbeiro = UUID.fromString(barbeiroId);
        UUID cliente = UUID.fromString(clienteId);
        UUID servico = UUID.fromString(servicoId);

        Barbeiro barbeiro1 = barbeiroService.buscarPorId(barbeiro);
        Cliente cliente1 = clienteService.buscarPorId(cliente);
        Servico servico1 = servicoService.buscarPorId(servico);

        TipoServico tipoServico1 = TipoServico.valueOf(tipoServico);

        Agendamento age = new Agendamento(
                UUID.randomUUID(),
                data,
                hora,
                barbeiro1,
                cliente1,
                servico1,
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
}
