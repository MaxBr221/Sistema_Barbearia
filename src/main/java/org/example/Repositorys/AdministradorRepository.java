package org.example.Repositorys;

import org.example.Dominios.Administrador;
import org.example.Dominios.Barbeiro;
import org.example.Dominios.Cliente;

import java.util.List;
import java.util.UUID;

public interface AdministradorRepository {
    Administrador buscarPorLogin(String login);

}
