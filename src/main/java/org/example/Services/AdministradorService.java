package org.example.Services;

import org.example.Dominios.Administrador;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.ClienteAtivo;
import org.example.ClienteNaoAtivo;
import org.example.Repositorys.AdministradorRepository;

import java.util.List;
import java.util.UUID;

public class AdministradorService {
    private AdministradorRepository administradorRepository;

    public AdministradorService(AdministradorRepository administradorRepository) {
        this.administradorRepository = administradorRepository;
    }
    public void cadastrarCliente(Cliente cliente){
        if (cliente == null){
            throw new IllegalArgumentException("Cliente vazio não é permitido cadastrar");
        }
        if(cliente.isAtivo()){
            throw new ClienteAtivo("Cliente " + cliente + "já está ativo!");
        }
        administradorRepository.cadastrarCliente(cliente);
    }
    public void cadastrarBarbeiro(Barbeiro barbeiro){
        if (barbeiro == null){
            throw new IllegalArgumentException("Barbeiro vazio não é permitido cadastrar");
        }
        administradorRepository.cadastrarBarbeiro(barbeiro);
    }
    public void removerCliente(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        administradorRepository.removerCliente(id);
    }
    public void removerBarbeiro(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        administradorRepository.removerBarbeiro(id);
    }
    public List<Cliente> listarClientes(){
        return administradorRepository.listarClientes();
    }
    public List<Barbeiro> listarBarbeiros(){
        return administradorRepository.listarBarbeiros();
    }
    public Cliente buscarPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        for (Cliente cliente: administradorRepository.listarClientes()){
            if (!cliente.isAtivo()){
                throw new ClienteNaoAtivo("Cliente não está ativo!");
            }
        }return administradorRepository.buscarClientePorId(id);
    }
    public Barbeiro buscarBarbeiroPorId(UUID id){
        if (id == null){
            throw new IllegalArgumentException("Não é permitido id nulo.");
        }
        return administradorRepository.buscarBarbeiroPorId(id);
    }
    public Administrador buscarPorLogin(String login){
        if (login == null){
            throw new IllegalArgumentException("Erro, não é permitido login nulo.");
        }
        return administradorRepository.buscarPorLogin(login);
    }

}
