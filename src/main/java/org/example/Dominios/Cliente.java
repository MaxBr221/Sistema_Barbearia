package org.example.Dominios;

import java.util.UUID;

public class Cliente extends Usuario {
    private UUID id;


    public Cliente(UUID id,String nome, String telefone, String login, String senha) {
        super(nome, telefone, login, Role.CLIENTE, senha);
        this.id = id;

    }

    public Cliente(String nome, String telefone, String login, String senha) {
        super(nome, telefone, login, senha);
    }

    public Cliente(UUID id) {
        this.id = id;
    }

    public Cliente() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isAtivo(){
        return true;
    }
}
