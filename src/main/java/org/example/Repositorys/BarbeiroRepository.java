package org.example.Repositorys;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Cliente;

import java.util.List;

public interface BarbeiroRepository {
    List<Agendamento> listarAgendamentos();
    List<Cliente> listarCliente();

}
