package org.example;

import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;
import org.example.Repositorys.AdministradorRepository;

import java.util.List;

public class AdministradorRepositoryImpl implements AdministradorRepository {
    @Override
    public void cadastrarBarbeiro(Barbeiro barbeiro) {

    }

    @Override
    public List<Cliente> listarClientes() {
        return List.of();
    }

    @Override
    public void cadastrarCliente(Cliente cliente) {

    }

    @Override
    public Cliente buscarClientePorId(String id) {
        return null;
    }

    @Override
    public Barbeiro buscarBarbeiroPorId(String id) {
        return null;
    }

    @Override
    public void removerBarbeiro(String id) {

    }

    @Override
    public void removerCliente(String id) {

    }

    @Override
    public List<Barbeiro> listarBarbeiros() {
        return List.of();
    }
}
