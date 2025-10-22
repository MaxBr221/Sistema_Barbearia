package org.example.Repositorys;

import org.example.Agendamento;
import org.example.Cliente;

import java.util.List;

public interface BarbeiroRepository {
    List<Agendamento> listarAgendamentos();
    List<Cliente> listarCliente();

}
