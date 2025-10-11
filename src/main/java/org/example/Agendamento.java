package org.example;

import java.rmi.server.UID;
import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private UID id;
    private LocalDate data;
    private LocalTime hora;
    private Barbeiro barbeiro;
    private Cliente cliente;
    private Servico servico;
    private Status status;

    public Agendamento(UID id, LocalDate data, LocalTime hora, Barbeiro barbeiro, Cliente cliente, Servico servico, Status status) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.barbeiro = barbeiro;
        this.cliente = cliente;
        this.servico = servico;
        this.status = status;
    }

    public Agendamento(UID id, LocalDate data, Cliente cliente, Servico servico, Status status, LocalTime hora) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.servico = servico;
        this.status = status;
        this.hora = hora;
    }

    public Agendamento() {
    }

    public UID getId() {
        return id;
    }

    public void setId(UID id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
