package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Repositorys.BarbeiroRepository;

import java.util.List;
import java.util.UUID;

public class BarbeiroService {
    private BarbeiroRepository barbeiroRepository;

    public BarbeiroService(BarbeiroRepository barbeiroRepository) {
        this.barbeiroRepository = barbeiroRepository;
    }
    public List<Agendamento> listarAgendamento(){
        return barbeiroRepository.listarAgendamentos();
    }
    public List<Cliente> listarClientes(){
        return barbeiroRepository.listarCliente();
    }
    public Barbeiro buscarPorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return barbeiroRepository.buscarPorLogin(login);
    }
    public void removerCliente(UUID id){
        if (id == null){
            throw new IllegalArgumentException("id não é permitido nulo.");
        }
        barbeiroRepository.removerCLiente(id);
    }
    public Barbeiro buscarPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Erro, valor nulo.");
        }
        return barbeiroRepository.buscarPorId(id);
    }
}
