package org.example;

import org.example.Dominios.Agendamento;
import org.example.Repositorys.AgendamentoRepository;

import java.util.List;

public class AgendamentoRepositoryImpl implements AgendamentoRepository {
    @Override
    public void adicionarAgendamento(Agendamento agendamento) {

    }

    @Override
    public void removerAgendamento(String id) {

    }

    @Override
    public List<Agendamento> listarAgendamentos() {
        return List.of();
    }

    @Override
    public Agendamento buscarAgendamentoPorId(String id) {
        return null;
    }
}
