package org.example.Dominios;

import java.util.UUID;

public class Barbeiro extends Usuario {
    private UUID id;

    public Barbeiro(UUID id,String nome, String telefone, String login, String senha) {
        super(nome, telefone, login, Role.BARBEIRO, senha);
        this.id = id;
    }

    public Barbeiro() {
    }

    public Barbeiro(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
