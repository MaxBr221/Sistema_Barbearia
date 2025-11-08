package org.example.Dominios;

import java.util.UUID;

public class Administrador extends Usuario {
    private UUID id;

    public Administrador(UUID id, String nome, String telefone, String login, String senha) {
        super(nome, telefone, login, Role.ADMIN, senha);
        this.id = id;
    }

    public Administrador() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
