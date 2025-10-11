package org.example;

import java.time.LocalDate;

public class Servico {
    private String nome;
    private LocalDate duracao;

    public Servico(String nome, LocalDate duracao) {
        this.nome = nome;
        this.duracao = duracao;
    }

    public String getNome() {
        return nome;
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
