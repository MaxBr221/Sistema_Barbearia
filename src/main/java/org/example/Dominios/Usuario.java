package org.example.Dominios;


public abstract class Usuario {
    private String nome;
    private String telefone;
    private String email;
    private Role role;


    public Usuario(String nome, String telefone, String email, Role role) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.role = role;
    }

    public Usuario() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
