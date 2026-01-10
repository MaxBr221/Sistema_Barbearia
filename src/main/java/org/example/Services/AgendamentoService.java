package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.Exceptions.AgendamentoNaoExistente;
import org.example.Exceptions.AgendamentoReservado;
import org.example.Repositorys.AgendamentoRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class AgendamentoService {
    private AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;

    }public void criar(Agendamento agendamento){
        validarData(agendamento.getData());
        if(agendamentoRepository.existeAgendamento(agendamento.getData(), agendamento.getHora())){
            throw new AgendamentoReservado("Agendamento já reservado nesse horario.");
        }
        agendamentoRepository.adicionarAgendamento(agendamento);
    }

    public void removerAgendamento(UUID id){
        boolean encontrado = false;
        for (Agendamento agendamento : agendamentoRepository.listarAgendamentos()) {
            if (agendamento.getId().equals(id)) {
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new AgendamentoNaoExistente("O agendamento com o id " + id + " não está cadastrado!");
        }
        agendamentoRepository.removerAgendamento(id);
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
    public List<LocalTime> buscarHorarisoOcupados(LocalDate data){
        if (data == null){
            throw new IllegalArgumentException("Não é permitido data nula");
        }
        return agendamentoRepository.buscarHorariosOcupadosPorData(data);
    }
    public boolean existeAgendamento(LocalDate data, LocalTime hora){
        if (data == null || hora == null){
            throw new IllegalArgumentException("Não é permitido data/hora nula");
        }
         return agendamentoRepository.existeAgendamento(data, hora);
    }
    public List<Agendamento> listarAgendamentosAtivos(UUID clienteId){
        if (clienteId == null){
            throw new IllegalArgumentException("não é permitido id nulo");
        }
        return agendamentoRepository.listarAgendamentoAtivos(clienteId);
    }
    public List<Agendamento> listarHistorico(UUID clienteId){
        if (clienteId == null){
            throw new IllegalArgumentException("não é permitido id nulo");
        }
        return agendamentoRepository.listarHistorico(clienteId);
    }
    public void validarData(LocalDate date){
        LocalDate hoje = LocalDate.now();

        if(date.isBefore(hoje)){
            throw new IllegalArgumentException("Não é permitido cadastrar datas passadas.");
        }
    }
}
