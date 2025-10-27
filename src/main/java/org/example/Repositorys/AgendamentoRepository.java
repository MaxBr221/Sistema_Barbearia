package org.example.Repositorys;

import org.example.Dominios.Agendamento;

import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository {
    void adicionarAgendamento(Agendamento agendamento);
    void removerAgendamento(UUID id);
    List<Agendamento> listarAgendamentos();
    Agendamento buscarAgendamentoPorId(UUID id);

}
