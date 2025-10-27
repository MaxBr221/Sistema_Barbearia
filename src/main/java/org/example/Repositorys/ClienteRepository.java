package org.example.Repositorys;

import org.example.Dominios.Cliente;
import java.util.List;
import java.util.UUID;

public interface ClienteRepository {
    void cadastrarCliente(Cliente cliente);
    void editarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorId(UUID id);

}
