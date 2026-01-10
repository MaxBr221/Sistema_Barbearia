package org.example.Dominios;


public abstract class Usuario {
    private String nome;
    private String telefone;
    private String login;
    private Role role;
    private String senha;

    public Usuario(String nome, String telefone, String login, Role role, String senha) {
        if (nome == null){
            throw new IllegalArgumentException("Erro,nome não pode ser nula");
        }
        this.nome = nome;
        if (telefone == null){
            throw new IllegalArgumentException("Erro,telefone não pode ser nula");
        }
        this.telefone = telefone;
        if (login == null){
            throw new IllegalArgumentException("Erro, login nome não pode ser nula");
        }
        this.login = login;
        if (role == null){
            throw new IllegalArgumentException("Erro,role nome não pode ser nula");
        }
        this.role = role;
        if (senha == null){
            throw new IllegalArgumentException("Erro,senha não pode ser nula");
        }
        this.senha = senha;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

    public Usuario(String nome, String telefone, String login, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.login = login;
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
