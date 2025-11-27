package org.example.Repositorys;

import org.example.Dominios.Administrador;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;

import java.util.List;
import java.util.UUID;

public interface AdministradorRepository {
    void cadastrarBarbeiro(Barbeiro barbeiro);
    List<Cliente> listarClientes();
    void cadastrarCliente(Cliente cliente);
    Cliente buscarClientePorId(UUID id);
    Barbeiro buscarBarbeiroPorId(UUID id);
    void removerBarbeiro(UUID id);
    void removerCliente(UUID id);
    List<Barbeiro> listarBarbeiros();
    Administrador buscarPorLogin(String login);
    Barbeiro buscarBarbeiroPorLogin(String login);
}
