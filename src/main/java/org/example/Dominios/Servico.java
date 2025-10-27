package org.example.Dominios;


import java.time.LocalTime;
import java.util.UUID;

public class Servico {
    private UUID id;
    private String nome;
    private LocalTime duracao;

    public Servico(UUID id, String nome, LocalTime duracao) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
    }

    public Servico(UUID id) {
        this.id = id;
    }

    public Servico() {
    }

    public String getNome() {
        return nome;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalTime getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalTime duracao) {
        this.duracao = duracao;
    }
}
