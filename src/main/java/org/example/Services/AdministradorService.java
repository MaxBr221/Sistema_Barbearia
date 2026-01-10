package org.example.Services;

import org.example.Dominios.Administrador;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Exceptions.ClienteAtivo;
import org.example.Exceptions.ClienteNaoAtivo;
import org.example.Repositorys.AdministradorRepository;
import org.example.Repositorys.BarbeiroRepository;
import org.example.Repositorys.ClienteRepository;

import java.util.List;
import java.util.UUID;

public class AdministradorService {
    private AdministradorRepository administradorRepository;
    private BarbeiroRepository barbeiroRepository;
    private ClienteRepository clienteRepository;

    public AdministradorService(AdministradorRepository administradorRepository, BarbeiroRepository barbeiroRepository, ClienteRepository clienteRepository) {
        this.administradorRepository = administradorRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.clienteRepository = clienteRepository;
    }

    public void cadastrarCliente(Cliente cliente){
        if (cliente == null){
            throw new IllegalArgumentException("Cliente vazio não é permitido cadastrar");
        }
        if(cliente.isAtivo()){
            throw new ClienteAtivo("Cliente " + cliente + "já está ativo!");
        }
        clienteRepository.cadastrarCliente(cliente);
    }
//    public void removerCliente(UUID id){
//        if (id == null){
//            throw new IllegalArgumentException("Não é permitido id nulo.");
//        }
//        administradorRepository.removerCliente(id);
//    }
//    public void removerBarbeiro(UUID id){
//        if (id == null){
//            throw new IllegalArgumentException("Não é permitido id nulo.");
//        }
//        administradorRepository.removerBarbeiro(id);
//    }
    public List<Cliente> listarClientes(){
        return clienteRepository.listarClientes();
    }

    public Cliente buscarPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        for (Cliente cliente: clienteRepository.listarClientes()){
            if (!cliente.isAtivo()){
                throw new ClienteNaoAtivo("Cliente não está ativo!");
            }
        }return clienteRepository.buscarClientePorId(id);
    }

    public Administrador buscarPorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return administradorRepository.buscarPorLogin(login);
    }
    public Barbeiro buscarBarbeiroPorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return barbeiroRepository.buscarPorLogin(login);
    }
    public Cliente buscarClientePorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return clienteRepository.buscarPorLogin(login);
    }
    public Cliente buscarClientePorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        return clienteRepository.buscarClientePorId(id);
    }

}
