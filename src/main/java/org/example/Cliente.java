package org.example;

import java.rmi.server.UID;

public class Cliente extends Usuario{
    private UID id;
    private String senha;

    public Cliente(String nome, String telefone, String email, UID id, String senha) {
        super(nome, telefone, email);
        this.id = id;
        this.senha = senha;
    }

    public Cliente() {
    }

    public UID getId() {
        return id;
    }

    public void setId(UID id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
