package org.example.Repositorys;

import org.example.Dominios.Agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository {
    void adicionarAgendamento(Agendamento agendamento);
    void removerAgendamento(UUID id);
    List<Agendamento> listarAgendamentos();
    Agendamento buscarAgendamentoPorId(UUID id);
    boolean existeAgendamento(LocalDate localDate, LocalTime localTime);
    List<LocalTime> buscarHorariosOcupadosPorData(LocalDate data);
    List<Agendamento> listarAgendamentoAtivos(UUID clienteId);

}
