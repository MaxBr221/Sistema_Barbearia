package org.example.Repositorys;

import org.example.Barbeiro;
import org.example.Cliente;

import java.util.List;

public interface AdministradorRepository {
    void cadastrarBarbeiro(Barbeiro barbeiro);
    List<Cliente> listarClientes();
    void cadastrarCliente(Cliente cliente);
    Cliente buscarClientePorId(String id);
    Barbeiro buscarBarbeiroPorId(String id);
    void removerBarbeiro(String id);
    void removerCliente(String id);
    List<Barbeiro> listarBarbeiros();
}
