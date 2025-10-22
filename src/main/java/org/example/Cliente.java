package org.example;

import java.util.UUID;

public class Cliente extends Usuario {
    private UUID id;
    private String senha;


    public Cliente(UUID id, String nome, String telefone, String email, String senha) {
        super(nome, telefone, email, Role.CLIENTE);
        this.id = id;
        this.senha = senha;

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
