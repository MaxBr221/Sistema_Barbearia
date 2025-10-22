package org.example.Dominios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Agendamento {
    private UUID id;
    private LocalDate data;
    private LocalTime hora;
    private Barbeiro barbeiro;
    private Cliente cliente;
    private Servico servico;
    private Status status;
    private TipoServico tipoServico;

    public Agendamento(UUID id, LocalDate data, LocalTime hora, Barbeiro barbeiro, Cliente cliente, Servico servico, Status status, TipoServico tipoServico) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.barbeiro = barbeiro;
        this.cliente = cliente;
        this.servico = servico;
        this.status = status;
        this.tipoServico = tipoServico;
    }

    public Agendamento(UUID id, LocalDate data, Cliente cliente, Servico servico, Status status, LocalTime hora, TipoServico tipoServico) {
        this.id = id;
        this.data = data;
        this.cliente = cliente;
        this.servico = servico;
        this.status = status;
        this.hora = hora;
        this.tipoServico = tipoServico;
    }

    public Agendamento() {
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
