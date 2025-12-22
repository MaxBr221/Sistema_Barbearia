package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Repositorys.AgendamentoRepository;
import org.example.Repositorys.BarbeiroRepository;
import org.example.Repositorys.ClienteRepository;

import java.util.List;
import java.util.UUID;

public class BarbeiroService {
    private BarbeiroRepository barbeiroRepository;
    private AgendamentoRepository agendamentoRepository;
    private ClienteRepository clienteRepository;

    public BarbeiroService(BarbeiroRepository barbeiroRepository, AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository) {
        this.barbeiroRepository = barbeiroRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;

    }
    public List<Agendamento> listarAgendamento(){
        return agendamentoRepository.listarAgendamentos();
    }
    public List<Cliente> listarClientes(){
        return clienteRepository.listarClientes();
    }
    public Barbeiro buscarPorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return barbeiroRepository.buscarPorLogin(login);
    }

    public Barbeiro buscarBarbeiroPadrao(){
        return barbeiroRepository.buscarBarbeiroPadrao()
                .orElseThrow(()-> new RuntimeException("barbeiro não encontrado."));
    }

}
