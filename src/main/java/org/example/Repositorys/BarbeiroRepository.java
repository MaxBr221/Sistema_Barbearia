package org.example.Repositorys;

import org.example.Dominios.Agendamento;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;

import java.util.List;
import java.util.UUID;

public interface BarbeiroRepository {
    List<Agendamento> listarAgendamentos();
    List<Cliente> listarCliente();
    Barbeiro buscarPorLogin(String login);
    void removerCLiente(UUID id);


}
