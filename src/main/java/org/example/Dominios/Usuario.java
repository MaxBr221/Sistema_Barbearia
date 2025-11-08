package org.example.Dominios;


public abstract class Usuario {
    private String nome;
    private String telefone;
    private String login;
    private Role role;
    private String senha;

    public Usuario(String nome, String telefone, String email, Role role, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.login = email;
        this.role = role;
        this.senha = senha;
    }

    public Usuario(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.login = email;
    }

    public Usuario() {
    }

    public Role getRole() {
        return role;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
