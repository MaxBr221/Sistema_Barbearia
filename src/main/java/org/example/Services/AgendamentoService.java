package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.AgendamentoNaoExistente;
import org.example.AgendamentoReservado;
import org.example.Dominios.Status;
import org.example.Repositorys.AgendamentoRepository;

import java.util.List;
import java.util.UUID;

public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }public void criar(Agendamento agendamento){
        if(agendamentoRepository.existeAgendamento(agendamento.getData(), agendamento.getHora(), agendamento.getBarbeiro().getId())){
            throw new AgendamentoReservado("Agendamento já reservado nesse horario.");
        }
        agendamentoRepository.adicionarAgendamento(agendamento);
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
    public void removerAgendamento(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo");
        }
        for (Agendamento agendamento: agendamentoRepository.listarAgendamentos()){
            if (!agendamento.getId().equals(id)){
                throw new AgendamentoNaoExistente("O agendamento com o id " + id + " não está cadastrado!");
            }
        }agendamentoRepository.removerAgendamento(id);
    }
    public Agendamento buscarAgendamentoPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo");
        }
        return agendamentoRepository.buscarAgendamentoPorId(id);
    }
    public List<Agendamento> listarAgendamento(){
        return agendamentoRepository.listarAgendamentos();
    }
}
