package org.example.Repositorys;

import org.example.Agendamento;

import java.util.List;

public interface AgendamentoRepository {
    void adicionarAgendamento(Agendamento agendamento);
    void removerAgendamento(String id);
    List<Agendamento> listarAgendamentos();
    Agendamento buscarAgendamentoPorId(String id);

}
