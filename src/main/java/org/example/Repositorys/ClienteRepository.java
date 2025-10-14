package org.example.Repositorys;

import org.example.Cliente;
import java.util.List;

public interface ClienteRepository {
    void cadastrarCliente(Cliente cliente);
    void editarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorId(String id);

}
