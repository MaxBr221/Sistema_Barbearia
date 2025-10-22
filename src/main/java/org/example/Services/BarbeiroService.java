package org.example.Services;

import org.example.Agendamento;
import org.example.Cliente;
import org.example.Repositorys.BarbeiroRepository;

import java.util.List;

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
}
