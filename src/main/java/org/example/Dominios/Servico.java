package org.example.Dominios;

import java.time.LocalDate;
import java.util.UUID;

public class Servico {
    private UUID id;
    private String nome;
    private LocalDate duracao;

    public Servico(UUID id, String nome, LocalDate duracao) {
        this.id = id;
        this.nome = nome;
        this.duracao = duracao;
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

    public LocalDate getDuracao() {
        return duracao;
    }

    public void setDuracao(LocalDate duracao) {
        this.duracao = duracao;
    }
}
