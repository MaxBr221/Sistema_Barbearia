package org.example.Services;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Cliente;
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
