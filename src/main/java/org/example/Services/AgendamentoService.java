package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.AgendamentoNaoExistente;
import org.example.AgendamentoReservado;
import org.example.Repositorys.AgendamentoRepository;

public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }
    public void adicionarAgendamento(Agendamento agendamento){
        if (agendamento == null){
            throw new IllegalArgumentException("Erro, agendamento nulo!");
        }
        for (Agendamento agendamento1:agendamentoRepository.listarAgendamentos()){
            if(agendamento1.getId().equals(agendamento.getId())){
                throw new AgendamentoReservado("Agendamento já existente!");
            }
        }
        agendamentoRepository.adicionarAgendamento(agendamento);
    }
    public void removerAgendamento(String id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo");
        }
        for (Agendamento agendamento: agendamentoRepository.listarAgendamentos()){
            if (!agendamento.getId().equals(id)){
                throw new AgendamentoNaoExistente("O agendamento com o id " + id + " não está cadastrado!");
            }
        }agendamentoRepository.removerAgendamento(id);
    }
    public Agendamento buscarAgendamentoPorId(String id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo");
        }
        return agendamentoRepository.buscarAgendamentoPorId(id);
    }
}
