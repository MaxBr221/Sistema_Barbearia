package org.example.Services;

import org.example.Cliente;
import org.example.ClienteAtivo;
import org.example.ClienteNaoAtivo;
import org.example.Repositorys.ClienteRepository;
import java.util.ArrayList;
import java.util.List;


public class ClienteService {
    private ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    public void cadastrarCliente(Cliente cliente){
        if(cliente == null){
            throw new IllegalArgumentException("Cliente nulo não é permitido!");
        }
        if(cliente.isAtivo()){
            throw new ClienteAtivo("Cliente já está ativo");
        }
        clienteRepository.cadastrarCliente(cliente);

    }
    public Cliente buscarPorId(String id){
        if(id == null){
            throw new IllegalArgumentException("Id nulo não é permitido!");
        }
        return clienteRepository.buscarClientePorId(id);
    }
    public void editarCliente(Cliente cliente){
        if(cliente == null){
            throw new IllegalArgumentException("Cliente nulo não é permitido!");
        }
        if (!cliente.isAtivo()){
            throw new ClienteNaoAtivo("Não é permitido editar cliente que não está ativo!");
        }
        clienteRepository.editarCliente(cliente);
    }
    public List<Cliente> listarClientes(){
        return clienteRepository.listarClientes();
    }
    public List<Cliente> listarClientesAtivos(){
        List<Cliente> clientesAtivos = new ArrayList<>();
        for(Cliente c: clienteRepository.listarClientes()){
            if (c.isAtivo()){
                clientesAtivos.add(c);
            }
        }
        return clientesAtivos;
    }

}
