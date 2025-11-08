package org.example.Dominios;

import java.util.UUID;

public class Cliente extends Usuario {
    private UUID id;
    private String senha;

    public Cliente(UUID id,String nome, String telefone, String login, String senha) {
        super(nome, telefone, login, Role.CLIENTE, senha);
        this.id = id;
        this.senha = senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isAtivo(){
        return true;
    }
}
